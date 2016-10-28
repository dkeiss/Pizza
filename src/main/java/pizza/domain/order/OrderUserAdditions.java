package pizza.domain.order;

import lombok.Data;
import pizza.domain.product.Product;
import pizza.domain.product.ProductVariation;
import pizza.domain.product.additional.Additional;
import pizza.domain.product.additional.AdditionalPrice;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Entity
@Data
public class OrderUserAdditions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_ID_SEQ")
    @SequenceGenerator(name = "ORDER_ID_SEQ", sequenceName = "ORDER_ID_SEQ", allocationSize = 100)
    private Integer id;

    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private OrderUser orderUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDITIONAL_ID")
    private Additional additional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDITIONAL_PRICE_ID")
    private AdditionalPrice additionalPrice;

    private Date creationDate;

    private Date modificationDate;

}
