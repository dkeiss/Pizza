package pizza.vo.order;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Daniel Keiss on 22.10.2016.
 */
@Data
public class UserOrderVO {

    private Integer userOrderId;

    private Integer userId;

    @NotNull
    private Integer productId;

    @NotNull
    private Integer productVariationId;

    @NotNull
    private Integer number = 1;

    private List<UserOrderAdditionalVO> userOrderAdditionals;

    private Integer productNumber;

}
