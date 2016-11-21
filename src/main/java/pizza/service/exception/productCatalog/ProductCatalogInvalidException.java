package pizza.service.exception.productCatalog;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Daniel Keiss on 20.11.2016.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProductCatalogInvalidException extends RuntimeException {

    public ProductCatalogInvalidException(UnrecognizedPropertyException e) {
        super(e);
    }

    @Override
    public String getMessage() {
        return "Product catalog is invalid! Check:" + getCause().getMessage();
    }

}
