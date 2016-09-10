package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.User;
import pizza.repositories.UserRepositories;

/**
 * Created by Daniel Keiss on 11.09.2016.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositories userRepositories;

    @Override
    public User getUserByAlias(String alias) {
        return userRepositories.findByAlias(alias);
    }

}
