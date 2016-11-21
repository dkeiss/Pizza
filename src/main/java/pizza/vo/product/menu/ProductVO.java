package pizza.vo.product.menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Data
public class ProductVO {

    private Integer productId;

    private Integer number;

    private String name;

    private String description;

    private List<ProductVariationVO> productVariations;

    private Date creationDate;

}
