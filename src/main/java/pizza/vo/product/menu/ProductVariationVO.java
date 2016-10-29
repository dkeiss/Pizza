package pizza.vo.product.menu;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Data
public class ProductVariationVO {

    private Integer productVariationId;

    private String name;

    private BigDecimal price;

}
