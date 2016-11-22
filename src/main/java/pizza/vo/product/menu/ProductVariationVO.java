package pizza.vo.product.menu;

import lombok.Data;
import pizza.domain.product.ProductVariationType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Data
public class ProductVariationVO {

    private Integer productVariationId;

    @Enumerated(EnumType.STRING)
    private ProductVariationType name;

    private BigDecimal price;

    private Date creationDate;

}
