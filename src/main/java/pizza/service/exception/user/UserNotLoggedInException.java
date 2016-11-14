package pizza.service.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Daniel Keiss on 14.11.2016.
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "User not logged in!")  // 400
public class UserNotLoggedInException extends RuntimeException {
}
