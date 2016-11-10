package pizza.vo.order;

import lombok.Data;

import java.util.Date;

/**
 * Created by Daniel Keiss on 24.10.2016.
 */
@Data
public class OrderActivateVO {

    private boolean active;

    private Date activeUntil;

}
