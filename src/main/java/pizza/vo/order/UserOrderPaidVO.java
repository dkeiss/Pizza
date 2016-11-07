package pizza.vo.order;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Daniel Keiss on 06.11.2016.
 */
@Data
public class UserOrderPaidVO {

    @NotNull
    private Boolean paid;

}
