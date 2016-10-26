package pizza.service.common;

import pizza.domain.BulkOrder;
import pizza.vo.order.BulkOrderVO;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * Created by Daniel Keiss on 26.10.2016.
 */
public interface ObjectMapperService {

    default <T> T copyFromObject(Object source, T destination) {
        copyProperties(source, destination, "creationDate", "modificationDate");
        return destination;
    }

    default <T> List<T> copyListFromObject(Iterable source, Class<T> destination) {
        List<T> destList = new ArrayList<>();
        source.forEach(o -> {
            T dest = null;
            try {
                dest = destination.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            copyProperties(o, dest);
            destList.add(dest);
        });
        return destList;
    }

}
