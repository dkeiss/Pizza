package pizza.repositories;

import org.springframework.data.repository.CrudRepository;
import pizza.domain.product.ProductCatalog;

/**
 * Created by Daniel Keiss on 29.10.2016.
 */
public interface ProductCatalogRepository extends CrudRepository<ProductCatalog, Integer> {

}
