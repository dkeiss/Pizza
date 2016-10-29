package pizza.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Daniel Keiss on 26.10.2016.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Object not found!")
public class NotFoundException extends RuntimeException {
}
