package pizza.domain.product;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Entity
@Data
public class ProductCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_CATALOG_ID_SEQ")
    @SequenceGenerator(name = "PRODUCT_CATALOG_ID_SEQ", sequenceName = "PRODUCT_CATALOG_ID_SEQ", allocationSize = 100)
    private Integer id;

    private String name;

    private Date creationDate;

}
