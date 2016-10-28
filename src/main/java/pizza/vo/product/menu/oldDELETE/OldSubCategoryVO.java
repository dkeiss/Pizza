package pizza.vo.product.menu.oldDELETE;

import lombok.Data;

import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Data
public class OldSubCategoryVO {

    private String name;

    private List<OldProductVO> products;

}
