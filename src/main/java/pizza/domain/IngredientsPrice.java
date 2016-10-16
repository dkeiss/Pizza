package pizza.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Daniel Keiss on 16.10.2016.
 */
@Entity
@Data
public class IngredientsPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INGREDIENTS_PRICE_ID_SEQ")
    @SequenceGenerator(name = "INGREDIENTS_PRICE_ID_SEQ", sequenceName = "INGREDIENTS_PRICE_ID_SEQ", allocationSize = 100)
    private Integer id;

}
