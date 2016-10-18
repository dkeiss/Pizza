package pizza.repositories;

import org.springframework.data.repository.CrudRepository;
import pizza.domain.User;

/**
 * Created by Daniel Keiss on 11.09.2016.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUserName(String alias);

}
