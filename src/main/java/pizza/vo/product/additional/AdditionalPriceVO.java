package pizza.vo.product.additional;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Daniel Keiss on 03.11.2016.
 */
@Data
public class AdditionalPriceVO {

    private Integer additionalPriceId;

    private String name;

    private BigDecimal price;

    private Date creationDate;

}
