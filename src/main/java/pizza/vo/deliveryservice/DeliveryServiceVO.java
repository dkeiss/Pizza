package pizza.vo.deliveryservice;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Daniel Keiss on 10.11.2016.
 */
@Data
public class DeliveryServiceVO {

    private Integer deliveryServiceId;

    @NotNull
    private String deliveryServiceName;

    private String firstName;

    private String lastName;

    private String street;

    private String streetNumber;

    private String postalCode;

    private String town;

    private Date creationDate;

    private Date modificationDate;

}
