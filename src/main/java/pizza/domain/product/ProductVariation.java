package pizza.domain.product;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Entity
@Data
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_VARIATION_ID_SEQ")
    @SequenceGenerator(name = "PRODUCT_VARIATION_ID_SEQ", sequenceName = "PRODUCT_VARIATION_ID_SEQ", allocationSize = 100)
    private Integer productVariationId;

    private String name;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

}
