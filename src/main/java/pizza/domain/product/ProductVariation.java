package pizza.domain.product;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Entity
@Data
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PRODUCT_VARIATION_ID_SEQ")
    @SequenceGenerator(name = "PRODUCT_VARIATION_ID_SEQ", sequenceName = "PRODUCT_VARIATION_ID_SEQ", allocationSize = 100)
    private Integer productVariationId;

    @Enumerated(EnumType.STRING)
    private ProductVariationType name;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private Date creationDate;

}
