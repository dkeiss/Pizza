package pizza.vo.order;

import lombok.Data;
import pizza.vo.product.additional.AdditionalInfoVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Daniel Keiss on 22.10.2016.
 */
@Data
public class UserOrderDetailsVO extends UserOrderVO {

    private String firstName;

    private String lastName;

    private String productName;

    private String productVariationName;

    private BigDecimal sum;

    private Boolean paid;

}
