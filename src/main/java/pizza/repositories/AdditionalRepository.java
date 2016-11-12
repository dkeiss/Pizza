package pizza.repositories;

import org.springframework.data.repository.CrudRepository;
import pizza.domain.product.additional.Additional;
import pizza.domain.product.additional.AdditionalCategory;

/**
 * Created by Daniel Keiss on 12.11.2016.
 */
public interface AdditionalRepository extends CrudRepository<Additional, Integer> {
}
