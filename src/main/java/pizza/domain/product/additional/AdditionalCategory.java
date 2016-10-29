package pizza.domain.product.additional;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Entity
@Data
public class AdditionalCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDITIONAL_CATEGORY_ID_SEQ")
    @SequenceGenerator(name = "ADDITIONAL_CATEGORY_ID_SEQ", sequenceName = "ADDITIONAL_CATEGORY_ID_SEQ", allocationSize = 100)
    private Integer additionalCategoryId;

    private String name;

    private Boolean duty;

    private Date creationDate;

}
