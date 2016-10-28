package pizza.service;

import pizza.domain.product.Product;

import java.util.List;

/**
 * Created by Daniel Keiss on 18.09.2016.
 */
public interface ProductService {

    List<Product> findAll();

}
