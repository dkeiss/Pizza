package pizza.service.exception.bulkorder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Daniel Keiss on 26.10.2016.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The \"active unil\" attribute is not in future!")
public class BulkOrderActiveUntilNotValidException extends RuntimeException {
}
