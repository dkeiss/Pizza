package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pizza.domain.order.BulkOrder;
import pizza.repositories.BulkOrderRepository;
import pizza.service.exception.BulkOrderActiveUntilNotValidException;
import pizza.service.exception.BulkOrderAlreadyActiveException;
import pizza.service.exception.BulkOrderProductCatalogNotExistsException;
import pizza.service.exception.NotFoundException;
import pizza.vo.order.BulkOrderVO;

import java.util.Date;
import java.util.List;

import static pizza.service.common.ObjectMapperUtil.copyFromBusinessObject;
import static pizza.service.common.ObjectMapperUtil.copyFromValueObject;
import static pizza.service.common.ObjectMapperUtil.copyListFromBusinessObject;

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
    private MailService mailService;

    @Override
    public List<BulkOrderVO> listBulkOrders() {
        return copyListFromBusinessObject(bulkOrderRepository.findAll(), BulkOrderVO.class);
    }

    @Override
    public BulkOrderVO createBulkOrder(BulkOrderVO bulkOrderVO) {
        isValid(bulkOrderVO);

        bulkOrderVO.setBulkOrderId(null);
        BulkOrder bulkOrderBO = copyFromValueObject(bulkOrderVO, new BulkOrder());
        bulkOrderBO.setCreationDate(new Date());
        bulkOrderBO = bulkOrderRepository.save(bulkOrderBO);
        bulkOrderVO = copyFromBusinessObject(bulkOrderBO, bulkOrderVO);

        mailService.sendBulkOrderInvitationToAll(bulkOrderVO.getName());

        return bulkOrderVO;
    }

    @Override
    public BulkOrderVO getBulkOrderById(Integer bulkorderId) {
        BulkOrder bulkOrder = bulkOrderRepository.findOne(bulkorderId);
        if (bulkOrder == null) {
            throw new NotFoundException();
        }
        return copyFromBusinessObject(bulkOrder, new BulkOrderVO());
    }

    @Override
    public void deleteBulkOrderById(Integer bulkorderId) {
        try {
            bulkOrderRepository.delete(bulkorderId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

    @Override
    public void updateBulkOrderById(BulkOrderVO bulkOrderVO) {
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
        if(activeBulkOrder == null){
            throw new NotFoundException();
        }
        return copyFromBusinessObject(activeBulkOrder, new BulkOrderVO());
    }

    public BulkOrder findActiveBulkOrder() {
        List<BulkOrder> activeBulkOrders = bulkOrderRepository.findByActiveUntilGreaterThan(new Date());
        if(activeBulkOrders.isEmpty()){
            return null;
        }
        if(activeBulkOrders.size() > 1){
            throw new RuntimeException("More than one bulk order active. Please contact your administrator!");
        }
        return activeBulkOrders.get(0);
    }

    private void isValid(BulkOrderVO bulkOrder) {
        isActiveUntilValid(bulkOrder);
        isAnotherBulkOrderActive();
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

}
