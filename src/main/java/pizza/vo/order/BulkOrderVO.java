package pizza.vo.order;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.beans.Transient;
import java.util.Date;

/**
 * Created by Daniel Keiss on 24.10.2016.
 */
@Data
public class BulkOrderVO {

    @NotNull
    private Integer bulkOrderId;

    @Getter
    private Integer catalogId;

    @NotNull
    private String name;

    @NotNull
    private Date activeUntil;

    @Getter
    private Boolean finished = false;

    private Date creationDate;

    private Date modificationDate;

    @Transient
    public boolean isActive() {
        return new Date().before(activeUntil);
    }

}
