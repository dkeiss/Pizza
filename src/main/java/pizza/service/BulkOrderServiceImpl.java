package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.order.BulkOrder;
import pizza.repositories.BulkOrderRepository;
import pizza.service.exception.*;
import pizza.service.exception.bulkorder.BulkOrderActiveUntilNotValidException;
import pizza.service.exception.bulkorder.BulkOrderAlreadyActiveException;
import pizza.service.exception.bulkorder.BulkOrderNotClosedException;
import pizza.service.exception.bulkorder.BulkOrderProductCatalogNotExistsException;
import pizza.vo.order.BulkOrderVO;
import pizza.vo.order.UserOrderDetailsVO;
import pizza.vo.user.UserVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static pizza.service.common.BulkOrderBusinessToValueObjectConverter.getBulkOrderFromBO;
import static pizza.service.common.BulkOrderValueToBusinessObjectConverter.createBulkOrderFromVO;
import static pizza.service.common.ObjectMapperUtil.copyFromValueObject;

/**
 * Created by Daniel Keiss on 26.10.2016.
 */
@Service
public class BulkOrderServiceImpl implements BulkOrderService {

    @Autowired
    private BulkOrderRepository bulkOrderRepository;

    @Autowired
    private ProductCatalogService productCatalogService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserOrderService userOrderService;

    @Autowired
    private MailService mailService;

    @Override
    public List<BulkOrderVO> listBulkOrders() {
        List<BulkOrderVO> bulkOrderVOs = new ArrayList<>();
        for (BulkOrder bulkOrder : bulkOrderRepository.findAll()) {
            bulkOrderVOs.add(getBulkOrderFromBO(bulkOrder));
        }
        return bulkOrderVOs;
    }

    @Override
    public BulkOrderVO createBulkOrder(BulkOrderVO bulkOrderVO) {
        isValid(bulkOrderVO);

        BulkOrder bulkOrderBO = createBulkOrderFromVO(bulkOrderVO);
        bulkOrderBO = bulkOrderRepository.save(bulkOrderBO);
        bulkOrderVO = getBulkOrderFromBO(bulkOrderBO);

        mailService.sendBulkOrderInvitationToAll(bulkOrderBO.getName(), getAllEmailAddresses());

        return bulkOrderVO;
    }

    @Override
    public BulkOrderVO getBulkOrderById(Integer bulkorderId) {
        BulkOrder bulkOrder = bulkOrderRepository.findOne(bulkorderId);
        if (bulkOrder == null) {
            throw new NotFoundException();
        }
        return getBulkOrderFromBO(bulkOrder);
    }

    @Override
    public void deactivateBulkOrderById(Integer bulkorderId) {
        BulkOrder bulkOrder = bulkOrderRepository.findOne(bulkorderId);
        if (bulkOrder == null) {
            throw new NotFoundException();
        }
        setFinished(bulkOrder);
        bulkOrderRepository.save(bulkOrder);
    }

    private void setFinished(BulkOrder bulkOrder) {
        if (bulkOrder.getActiveUntil().after(new Date())) {
            bulkOrder.setActiveUntil(new Date());
        }
        bulkOrder.setFinished(true);
    }

    @Override
    public void updateBulkOrder(BulkOrderVO bulkOrderVO) {
        BulkOrder bulkOrder = bulkOrderRepository.findOne(bulkOrderVO.getBulkOrderId());
        if (bulkOrder == null) {
            throw new NotFoundException();
        }
        copyFromValueObject(bulkOrderVO, bulkOrder);
        bulkOrder.setModificationDate(new Date());
        bulkOrderRepository.save(bulkOrder);
    }

    @Override
    public BulkOrderVO getActiveBulkOrder() {
        BulkOrder activeBulkOrder = findActiveBulkOrder();
        if (activeBulkOrder == null) {
            throw new NotFoundException();
        }
        return getBulkOrderFromBO(activeBulkOrder);
    }

    public BulkOrder findActiveBulkOrder() {
        List<BulkOrder> activeBulkOrders = bulkOrderRepository.findByActiveUntilGreaterThan(new Date());
        if (activeBulkOrders.isEmpty()) {
            return null;
        }
        if (activeBulkOrders.size() > 1) {
            throw new RuntimeException("More than one bulk order active. Please contact your administrator!");
        }
        return activeBulkOrders.get(0);
    }

    @Override
    public BulkOrderVO finishOpenBulkOrder() {
        BulkOrder openBulkOrder = findOpenBulkOrder();
        if (openBulkOrder == null) {
            throw new NotFoundException();
        }
        mailService.sendBulkOrderFinishedToSubscribers(openBulkOrder.getName(), getAllEmailAddressesFromCurrentUserOrders());

        setFinished(openBulkOrder);
        bulkOrderRepository.save(openBulkOrder);

        return getBulkOrderFromBO(openBulkOrder);
    }

    @Override
    public BulkOrder findOpenBulkOrder() {
        List<BulkOrder> openBulkOrder = bulkOrderRepository.findByFinished(false);
        if (openBulkOrder.isEmpty()) {
            return null;
        }
        if (openBulkOrder.size() > 1) {
            throw new RuntimeException("There exist more than one open bulk order!");
        }
        return openBulkOrder.get(0);
    }

    @Override
    public BulkOrderVO getOpenBulkOrder() {
        BulkOrder openBulkOrder = findOpenBulkOrder();
        if (openBulkOrder == null) {
            throw new NotFoundException();
        }
        return getBulkOrderFromBO(openBulkOrder);
    }

    private void isValid(BulkOrderVO bulkOrder) {
        isActiveUntilValid(bulkOrder);
        isAnotherBulkOrderActive();
        isAnotherBulkOrderNotFinished();
        productCatalogExists(bulkOrder.getCatalogId());
    }

    private void productCatalogExists(Integer productCatalogId) {
        if (!productCatalogService.productCatalogExists(productCatalogId)) {
            throw new BulkOrderProductCatalogNotExistsException();
        }
    }

    private void isActiveUntilValid(BulkOrderVO bulkOrder) {
        if (!bulkOrder.isActive()) {
            throw new BulkOrderActiveUntilNotValidException();
        }
    }

    private void isAnotherBulkOrderActive() {
        if (!bulkOrderRepository.findByActiveUntilGreaterThan(new Date()).isEmpty()) {
            throw new BulkOrderAlreadyActiveException();
        }
    }

    private void isAnotherBulkOrderNotFinished() {
        List<BulkOrder> openBulkOrder = bulkOrderRepository.findByFinished(false);
        if (!openBulkOrder.isEmpty()) {
            throw new BulkOrderNotClosedException();
        }
    }

    public List<String> getAllEmailAddresses() {
        List<UserVO> users = userService.getUsers();
        return users.stream().map(UserVO::getUserName).collect(Collectors.toList());
    }

    public List<String> getAllEmailAddressesFromCurrentUserOrders() {
        List<UserOrderDetailsVO> currentUserOrders = userOrderService.getCurrentUserOrders();
        return currentUserOrders.stream().map(UserOrderDetailsVO::getUserName).collect(Collectors.toList());
    }

}
