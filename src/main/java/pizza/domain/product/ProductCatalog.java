package pizza.domain.product;

import lombok.Data;
import pizza.vo.product.menu.ProductCategoryVO;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Entity
@Data
public class ProductCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_CATALOG_ID_SEQ")
    @SequenceGenerator(name = "PRODUCT_CATALOG_ID_SEQ", sequenceName = "PRODUCT_CATALOG_ID_SEQ", allocationSize = 100)
    private Integer productCatalogId;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productCatalog", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<ProductCategory> productCategories;

    private Date creationDate;

}
