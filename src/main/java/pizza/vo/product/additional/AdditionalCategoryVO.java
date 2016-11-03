package pizza.vo.product.additional;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Keiss on 03.11.2016.
 */
@Data
public class AdditionalCategoryVO {

    private Integer additionalCategoryId;

    private String name;

    private Boolean duty;

    private List<Integer> productIds;

    private List<AdditionalVO> additionals;

    private Date creationDate;

}
