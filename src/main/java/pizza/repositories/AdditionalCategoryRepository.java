package pizza.repositories;

import org.springframework.data.repository.CrudRepository;
import pizza.domain.product.additional.AdditionalCategory;

/**
 * Created by Daniel Keiss on 03.11.2016.
 */
public interface AdditionalCategoryRepository extends CrudRepository<AdditionalCategory, Integer> {
}
