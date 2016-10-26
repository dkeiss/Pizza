package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.BulkOrder;
import pizza.domain.User;
import pizza.repositories.BulkOrderRepository;
import pizza.service.common.ObjectMapperService;
import pizza.service.exception.BulkOrderNotFoundException;
import pizza.service.exception.UserNotFoundException;
import pizza.vo.order.BulkOrderVO;

import java.util.Date;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * Created by Daniel Keiss on 26.10.2016.
 */
@Service
public class BulkOrderServiceImpl implements BulkOrderService, ObjectMapperService {

    @Autowired
    private BulkOrderRepository bulkOrderRepository;

    @Override
    public List<BulkOrderVO> listBulkOrders() {
        return copyListFromObject(bulkOrderRepository.findAll(), BulkOrderVO.class);
    }

    @Override
    public BulkOrderVO createBulkOrder(BulkOrderVO bulkOrderVO) {
        checkIfBulkOrderIsValid(bulkOrderVO);
        bulkOrderVO.setId(null);
        BulkOrder bulkOrderBO = copyFromObject(bulkOrderVO, new BulkOrder());
        bulkOrderBO.setCreationDate(new Date());
        bulkOrderRepository.save(bulkOrderBO);
        return copyFromObject(bulkOrderBO, bulkOrderVO);
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

    private void checkIfBulkOrderIsValid(BulkOrderVO bulkOrder) {
        // TODO
    }

}
