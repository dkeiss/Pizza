package pizza.vo.product.menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import pizza.vo.product.additional.AdditionalCategoryVO;
import pizza.vo.product.additional.AdditionalVO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Daniel Keiss on 06.11.2016.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCatalogFullVO {

    @NotNull
    private ProductCatalogVO productCatalog;

    @NotNull
    private List<AdditionalCategoryVO> additionals;

}
