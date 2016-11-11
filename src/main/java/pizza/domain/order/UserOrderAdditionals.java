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
public class UserOrderAdditionals {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_ORDER_ADDITIONALS_ID_SEQ")
    @SequenceGenerator(name = "USER_ORDER_ADDITIONALS_ID_SEQ", sequenceName = "USER_ORDER_ADDITIONALS_ID_SEQ", allocationSize = 100)
    private Integer userOrderAdditionalsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ORDER_ID")
    private UserOrder userOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDITIONAL_ID")
    private Additional additional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDITIONAL_PRICE_ID")
    private AdditionalPrice additionalPrice;

    private Date creationDate;

    private Date modificationDate;

}
