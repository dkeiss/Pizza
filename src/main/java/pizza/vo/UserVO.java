package pizza.vo;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Daniel Keiss on 16.10.2016.
 */
@Data
public class UserVO {

    private Integer id;

    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String username;
    private BigDecimal discount;
    private boolean admin;
    private Date creationDate;
    private Date modificationDate;

}
