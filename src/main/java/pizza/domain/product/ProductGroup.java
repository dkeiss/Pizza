package pizza.domain.product;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Entity
@Data
public class ProductGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_GROUP_ID_SEQ")
    @SequenceGenerator(name = "PRODUCT_GROUP_ID_SEQ", sequenceName = "PRODUCT_GROUP_ID_SEQ", allocationSize = 100)
    private Integer productGroupId;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_CATEGORY_ID")
    private ProductCategory productCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productGroup", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Product> products;

    private Date creationDate;

}
