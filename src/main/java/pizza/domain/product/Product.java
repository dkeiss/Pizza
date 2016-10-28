package pizza.domain.product;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Daniel Keiss on 28.09.2016.
 */
@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_ID_SEQ")
    @SequenceGenerator(name = "PRODUCT_ID_SEQ", sequenceName = "PRODUCT_ID_SEQ", allocationSize = 100)
    private Integer id;

    private Integer number;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_GROUP_ID")
    private ProductGroup productGroup;

    private Date creationDate;

}
