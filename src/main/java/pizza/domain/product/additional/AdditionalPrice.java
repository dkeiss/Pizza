package pizza.domain.product.additional;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Entity
@Data
public class AdditionalPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ADDITIONAL_PRICE_ID_SEQ")
    @SequenceGenerator(name = "ADDITIONAL_PRICE_ID_SEQ", sequenceName = "ADDITIONAL_PRICE_ID_SEQ", allocationSize = 100)
    private Integer additionalPriceId;

    private String name;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDITIONAL_ID")
    private Additional additional;

    private Date creationDate;

}
