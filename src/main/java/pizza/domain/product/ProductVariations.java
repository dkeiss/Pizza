package pizza.domain.product;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Entity
@Data
public class ProductVariations {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_VARIATIONS_ID_SEQ")
    @SequenceGenerator(name = "PRODUCT_VARIATIONS_ID_SEQ", sequenceName = "PRODUCT_VARIATIONS_ID_SEQ", allocationSize = 100)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VARIATION_ID")
    private ProductVariation productVariation;

}
