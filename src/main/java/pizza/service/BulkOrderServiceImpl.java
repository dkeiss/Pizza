package pizza.service;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pizza.domain.order.BulkOrder;
import pizza.repositories.BulkOrderRepository;
import pizza.service.common.ObjectMapperUtil;
import pizza.service.exception.BulkOrderActiveUntilNotValidException;
import pizza.service.exception.BulkOrderAlreadyActiveException;
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

    private void isValid(BulkOrderVO bulkOrder) {
        isActiveUntilValid(bulkOrder);
        isAnotherBulkOrderActive();
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
