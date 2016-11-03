package pizza.vo.product.additional.old;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Daniel Keiss on 26.09.2016.
 */
@Data
public class AdditionalMenuBsVO {

    public String name;
    public List<BigDecimal> prices;

}
