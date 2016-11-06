package pizza.vo.order;

import lombok.Data;
import pizza.vo.product.additional.AdditionalInfoVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Daniel Keiss on 22.10.2016.
 */
@Data
public class UserOrderVO {

    private Integer userOrderId;

    private Integer userId;

    private String firstName;

    private String lastName;

    private Integer productId;

    private String productName;

    private Integer productVariationId;

    private String productVariationName;

    private List<AdditionalInfoVO> additionals;

    private BigDecimal sum;

    private Boolean paid;

}
