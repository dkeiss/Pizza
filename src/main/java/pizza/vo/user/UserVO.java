package pizza.vo.user;

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
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String userName;
    private BigDecimal discount;
    private boolean isAdmin;
    private Date creationDate;
    private Date modificationDate;

}
