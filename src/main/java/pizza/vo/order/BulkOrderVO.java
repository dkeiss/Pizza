package pizza.vo.order;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Daniel Keiss on 24.10.2016.
 */
@Data
public class BulkOrderVO {

    @NotNull
    private Integer bulkOrderId;

    @NotNull
    private Integer catalogId;

    @NotNull
    private String name;

    @NotNull
    private Date activeUntil;

    public boolean isActive() {
        return new Date().before(activeUntil);
    }

}
