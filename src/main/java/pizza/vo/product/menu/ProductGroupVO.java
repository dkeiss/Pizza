package pizza.vo.product.menu;

import lombok.Data;
import pizza.domain.product.Product;

import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Data
public class ProductGroupVO {

    private String name;

    private List<ProductVO> products;

}
