package pizza.domain.order;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Daniel Keiss on 26.10.2016.
 */
@Entity
@Data
public class BulkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BULK_ORDER_ID_SEQ")
    @SequenceGenerator(name = "BULK_ORDER_ID_SEQ", sequenceName = "BULK_ORDER_ID_SEQ", allocationSize = 100)
    private Integer bulkOrderId;

    private Integer catalogId;

    private String name;

    private Date activeUntil;

    private Boolean finished = false;

    private Date creationDate;

    private Date modificationDate;

}
