package pizza.domain.order;

import lombok.Data;
import pizza.domain.product.Product;
import pizza.domain.product.ProductVariation;
import pizza.domain.product.additional.Additional;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Entity
@Data
public class OrderUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ORDER_ID_SEQ")
    @SequenceGenerator(name = "ORDER_ID_SEQ", sequenceName = "ORDER_ID_SEQ", allocationSize = 100)
    private Integer orderUserId;

    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BULK_ORDER_ID")
    private BulkOrder bulkOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VARIATION_ID")
    private ProductVariation productVariation;

    private Date creationDate;

    private Date modificationDate;

}
