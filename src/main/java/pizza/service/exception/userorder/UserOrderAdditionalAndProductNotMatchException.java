package pizza.service.exception.userorder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Daniel Keiss on 21.11.2016.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Additional and product doesn't match!")
public class UserOrderAdditionalAndProductNotMatchException extends RuntimeException {
}
