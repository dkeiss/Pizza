package pizza.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by Daniel Keiss on 16.10.2016.
 */
@Data
public class UserVO {

    private Long id;

    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String username;
    private BigDecimal rabatt;
    private boolean admin;

}
