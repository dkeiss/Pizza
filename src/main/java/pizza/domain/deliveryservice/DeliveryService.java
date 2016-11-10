package pizza.domain.deliveryservice;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Daniel Keiss on 10.11.2016.
 */
@Entity
@Data
public class DeliveryService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "DELIVERY_SERVICE_ID_SEQ")
    @SequenceGenerator(name = "DELIVERY_SERVICE_ID_SEQ", sequenceName = "DELIVERY_SERVICE_ID_SEQ", allocationSize = 100)
    private Integer deliveryServiceId;

    private String deliveryServiceName;

    private String firstName;

    private String lastName;

    private String street;

    private String postalCode;

    private String town;

    private Date creationDate;

    private Date modificationDate;

}
