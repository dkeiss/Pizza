package pizza.domain.product.additional;

import lombok.Data;
import pizza.domain.product.Product;
import pizza.domain.product.ProductVariation;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Entity
@Data
public class AdditionalCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDITIONAL_CATEGORIES_ID_SEQ")
    @SequenceGenerator(name = "ADDITIONAL_CATEGORIES_ID_SEQ", sequenceName = "ADDITIONAL_CATEGORIES_ID_SEQ", allocationSize = 100)
    private Integer additionalCategoriesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDITIONAL_CATEGORY_ID")
    private AdditionalCategory additionalCategory;

    private Date creationDate;

}
