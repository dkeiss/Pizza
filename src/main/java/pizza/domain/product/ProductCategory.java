package pizza.domain.product;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import pizza.vo.product.menu.ProductGroupVO;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Entity
@Data
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_CATEGORY_ID_SEQ")
    @SequenceGenerator(name = "PRODUCT_CATEGORY_ID_SEQ", sequenceName = "PRODUCT_CATEGORY_ID_SEQ", allocationSize = 100)
    private Integer productCategoryId;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_CATALOG_ID")
    private ProductCatalog productCatalog;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productCategory", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<ProductGroup> productGroups;

    private Date creationDate;

}
