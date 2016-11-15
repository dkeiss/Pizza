package pizza.vo.order;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * Created by Daniel Keiss on 22.10.2016.
 */
@Data
public class UserOrderAdditionalVO {

    private Integer userOrderAdditionalId;

    @Getter
    private Integer userOrderId;

    @NotNull
    private Integer additionalId;

    @Getter
    private String additionalDescription;

    @NotNull
    private Integer additionalPriceId;

    @Getter
    private String additionalPriceName;

}
