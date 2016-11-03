package pizza.vo.product.additional;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Keiss on 03.11.2016.
 */
@Data
public class AdditionalVO {

    private Integer additionalId;

    private String description;

    private List<AdditionalPriceVO> additionalPrices;

    private Date creationDate;

}
