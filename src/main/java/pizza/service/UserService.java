package pizza.service;

import pizza.domain.User;

/**
 * Created by Daniel Keiss on 11.09.2016.
 */
public interface UserService {

    User getUserByAlias(String alias);

    boolean isUserPasswordValid(String name, String password);

}
