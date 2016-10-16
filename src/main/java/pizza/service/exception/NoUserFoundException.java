package pizza.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Daniel Keiss on 16.10.2016.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No User found")  // 404
public class NoUserFoundException extends RuntimeException{
}
