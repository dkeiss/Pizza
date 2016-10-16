package pizza.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Daniel Keiss on 16.10.2016.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Username already used!")  // 400
public class UsernameAlreadyUsedException extends RuntimeException {
}
