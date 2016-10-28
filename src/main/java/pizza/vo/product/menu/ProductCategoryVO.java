package pizza.vo.product.menu;

import lombok.Data;

import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Data
public class ProductCategoryVO {

    private String name;

    private List<ProductGroupVO> productGroups;

}
