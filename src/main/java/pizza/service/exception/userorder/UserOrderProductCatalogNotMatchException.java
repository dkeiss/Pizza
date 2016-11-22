package pizza.service.exception.userorder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Daniel Keiss on 21.11.2016.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Current order and active product catalog doesn't match!")
public class UserOrderProductCatalogNotMatchException extends RuntimeException {
}
