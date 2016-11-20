package pizza.vo.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Daniel Keiss on 22.10.2016.
 */
@Data
public class UserOrderDetailsVO extends UserOrderVO {

    private String firstName;

    private String lastName;

    private String userName;

    private String productName;

    private String productVariationName;

    private BigDecimal sum;

    private Boolean paid;

}
