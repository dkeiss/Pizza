package pizza.service.common;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * Created by Daniel Keiss on 26.10.2016.
 */
public class ObjectMapperUtil {

    public static <T> T copyFromValueObject(Object source, T destination) {
        copyProperties(source, destination, "creationDate", "modificationDate");
        return destination;
    }

    public static <T> T copyFromBusinessObject(Object source, T destination) {
        copyProperties(source, destination);
        return destination;
    }

    public static <T> List<T> copyListFromBusinessObject(Iterable source, Class<T> destination) {
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
