package pizza.service;

/**
 * Created by Daniel Keiss on 11.09.2016.
 */
public interface UserService {

    boolean isUsernameValid(String username);

    boolean isUsernameAndPasswordValid(String username, String password);

    boolean isAdmin(String alias);

}
