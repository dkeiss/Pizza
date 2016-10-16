package pizza.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Daniel Keiss on 16.10.2016.
 */
@Data
public class UserVO {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private BigDecimal rabatt;
    private boolean admin;

}
