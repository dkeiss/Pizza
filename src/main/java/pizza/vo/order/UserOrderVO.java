package pizza.vo.order;

import lombok.Data;
import lombok.Getter;
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

    private Integer productId;

    private Integer productVariationId;

    private List<Integer> additionalIds;

}
