package pizza.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Daniel Keiss on 07.11.2016.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No product catalog active!")
public class BulkOrderProductCatalogNotExistsException extends RuntimeException {
}
