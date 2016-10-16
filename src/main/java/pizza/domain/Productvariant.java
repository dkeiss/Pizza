package pizza.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Daniel Keiss on 16.10.2016.
 */
@Entity
@Data
public class Productvariant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "PRODUCT_ID_SEQ", allocationSize = 100)
    private Integer id;

}
