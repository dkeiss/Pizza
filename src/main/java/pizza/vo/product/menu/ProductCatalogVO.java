package pizza.vo.product.menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCatalogVO {

    private Integer productCatalogId;

    private String name;

    private List<ProductCategoryVO> productCategories;

}
