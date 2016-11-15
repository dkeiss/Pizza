package pizza.vo.product.menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductVariationVO {

    private Integer productVariationId;

    private String name;

    private BigDecimal price;

    private Date creationDate;

}
