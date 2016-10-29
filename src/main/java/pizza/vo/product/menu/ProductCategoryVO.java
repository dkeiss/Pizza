package pizza.vo.product.menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCategoryVO {

    private Integer productCategoryId;

    private String name;

    private List<ProductGroupVO> productGroups;

    private Date creationDate;

}
