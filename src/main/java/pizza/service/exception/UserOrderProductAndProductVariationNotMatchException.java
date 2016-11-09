package pizza.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Daniel Keiss on 07.11.2016.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Product and product variation doesn't match!")
public class UserOrderProductAndProductVariationNotMatchException extends RuntimeException {
}
