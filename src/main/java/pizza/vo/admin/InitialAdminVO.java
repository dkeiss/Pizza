package pizza.vo.admin;

import lombok.Data;

/**
 * Created by Daniel Keiss on 14.11.2016.
 */
@Data
public class InitialAdminVO {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String passwordCheck;

}
