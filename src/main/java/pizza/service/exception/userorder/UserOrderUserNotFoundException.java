package pizza.service.exception.userorder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Daniel Keiss on 07.11.2016.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User doesn't exist!")
public class UserOrderUserNotFoundException extends RuntimeException {
}
