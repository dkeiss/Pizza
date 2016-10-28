package pizza.vo.product.menu.oldDELETE;

import lombok.Data;

import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Data
public class OldProductCategoryVO {

    private String name;

    private List<OldSubCategoryVO> subCategories;

}
