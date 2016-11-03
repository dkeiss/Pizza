package pizza.domain.product.additional;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Entity
@Data
public class AdditionalCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ADDITIONAL_CATEGORY_ID_SEQ")
    @SequenceGenerator(name = "ADDITIONAL_CATEGORY_ID_SEQ", sequenceName = "ADDITIONAL_CATEGORY_ID_SEQ", allocationSize = 100)
    private Integer additionalCategoryId;

    private String name;

    private Boolean duty;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "additionalCategory", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Additional> additionals;

    private String productIds;

    private Date creationDate;

}
