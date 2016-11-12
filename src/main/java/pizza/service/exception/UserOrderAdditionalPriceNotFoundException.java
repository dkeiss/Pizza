package pizza.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Daniel Keiss on 12.11.2016.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Additional price doesn't exist!")
public class UserOrderAdditionalPriceNotFoundException extends RuntimeException {
}
