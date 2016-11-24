package pizza.domain.order;

import lombok.Data;
import pizza.domain.product.Product;
import pizza.domain.product.ProductVariation;
import pizza.domain.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Entity
@Data
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_ORDER_ID_SEQ")
    @SequenceGenerator(name = "USER_ORDER_ID_SEQ", sequenceName = "USER_ORDER_ID_SEQ", allocationSize = 100)
    private Integer userOrderId;

    private BigDecimal amount;

	private BigDecimal discount;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BULK_ORDER_ID")
    private BulkOrder bulkOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VARIATION_ID")
    private ProductVariation productVariation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userOrder", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<UserOrderAdditional> userOrderAdditionals;

    private Integer number;

    private Boolean paid;

    private Date creationDate;

    private Date modificationDate;

}
