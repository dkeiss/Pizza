package pizza.repositories;

import org.springframework.data.repository.CrudRepository;
import pizza.domain.product.ProductVariation;

/**
 * Created by Daniel Keiss on 08.11.2016.
 */
public interface ProductVariationRepository extends CrudRepository<ProductVariation, Integer> {
}
