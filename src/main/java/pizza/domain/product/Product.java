package pizza.domain.product;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import pizza.vo.product.menu.ProductVariationVO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Keiss on 28.09.2016.
 */
@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PRODUCT_ID_SEQ")
    @SequenceGenerator(name = "PRODUCT_ID_SEQ", sequenceName = "PRODUCT_ID_SEQ", allocationSize = 100)
    private Integer productId;

    private Integer number;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_GROUP_ID")
    private ProductGroup productGroup;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<ProductVariation> productVariations;

    private Date creationDate;

}
