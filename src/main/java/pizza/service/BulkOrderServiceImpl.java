package pizza.service;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.order.BulkOrder;
import pizza.repositories.BulkOrderRepository;
import pizza.service.common.ObjectMapperService;
import pizza.service.exception.BulkOrderActiveUntilNotValidException;
import pizza.service.exception.BulkOrderAlreadyActiveException;
import pizza.service.exception.BulkOrderNotFoundException;
import pizza.vo.order.BulkOrderVO;

import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Keiss on 26.10.2016.
 */
@Service
public class BulkOrderServiceImpl implements BulkOrderService, ObjectMapperService {

    @Autowired
    private BulkOrderRepository bulkOrderRepository;

    @Autowired
    private MailService mailService;

    @Override
    public List<BulkOrderVO> listBulkOrders() {
        return copyListFromObject(bulkOrderRepository.findAll(), BulkOrderVO.class);
    }

    @Override
    public BulkOrderVO createBulkOrder(BulkOrderVO bulkOrderVO) {
        isValid(bulkOrderVO);

        bulkOrderVO.setId(null);
        BulkOrder bulkOrderBO = copyFromObject(bulkOrderVO, new BulkOrder());
        bulkOrderBO.setCreationDate(new Date());
        bulkOrderRepository.save(bulkOrderBO);
        bulkOrderVO = copyFromObject(bulkOrderBO, bulkOrderVO);

        try {
            mailService.sendBulkOrderInvitationToAll(bulkOrderVO.getName());
        } catch (EmailException e) {
            e.printStackTrace();
        }

        return bulkOrderVO;
    }

    @Override
    public BulkOrderVO getBulkOrderById(Integer bulkorderId) {
        BulkOrder bulkOrder = bulkOrderRepository.findOne(bulkorderId);
        return copyFromObject(bulkOrder, new BulkOrderVO());
    }

    @Override
    public void deleteBulkOrderById(Integer bulkorderId) {
        bulkOrderRepository.delete(bulkorderId);
    }

    @Override
    public void updateBulkOrderById(BulkOrderVO bulkOrderVO) {
        BulkOrder bulkOrder = bulkOrderRepository.findOne(bulkOrderVO.getId());
        if (bulkOrder == null) {
            throw new BulkOrderNotFoundException();
        }
        copyFromObject(bulkOrderVO, bulkOrder);
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
        if(!bulkOrderRepository.findByActiveUntilGreaterThan(new Date()).isEmpty()){
            throw new BulkOrderAlreadyActiveException();
        }
    }

}
