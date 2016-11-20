package pizza.vo.product.menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Data
public class ProductGroupVO {

    private Integer productGroupId;

    private String name;

    private List<ProductVO> products;

    private Date creationDate;

}
