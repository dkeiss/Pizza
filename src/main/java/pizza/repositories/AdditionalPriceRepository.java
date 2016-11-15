package pizza.repositories;

import org.springframework.data.repository.CrudRepository;
import pizza.domain.product.additional.AdditionalPrice;

/**
 * Created by Daniel Keiss on 12.11.2016.
 */
public interface AdditionalPriceRepository extends CrudRepository<AdditionalPrice, Integer> {
}
