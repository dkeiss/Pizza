package pizza.repositories;

import org.springframework.data.repository.CrudRepository;
import pizza.domain.Product;
import pizza.domain.User;

/**
 * Created by Daniel Keiss on 18.09.2016.
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {
}
