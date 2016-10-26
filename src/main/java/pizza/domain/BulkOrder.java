package pizza.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Daniel Keiss on 26.10.2016.
 */
@Entity
@Data
public class BulkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BULK_ORDER_ID_SEQ")
    @SequenceGenerator(name = "BULK_ORDER_ID_SEQ", sequenceName = "BULK_ORDER_ID_SEQ", allocationSize = 100)
    private Integer id;

    private Integer catalogId;

    private String name;

    private Date activeUntil;

    private Date creationDate;

    private Date modificationDate;

}
