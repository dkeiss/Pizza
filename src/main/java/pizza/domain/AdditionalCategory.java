package pizza.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Daniel Keiss on 16.10.2016.
 */
@Entity
@Data
public class AdditionalCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDITIONAL_CATEGORY_ID_SEQ")
    @SequenceGenerator(name = "ADDITIONAL_CATEGORY_ID_SEQ", sequenceName = "ADDITIONAL_CATEGORY_ID_SEQ", allocationSize = 100)
    private Integer id;

}
