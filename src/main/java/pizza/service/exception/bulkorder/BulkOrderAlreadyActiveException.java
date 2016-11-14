package pizza.service.exception.bulkorder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Keissenator on 27.10.2016.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Another buld order is already active!")
public class BulkOrderAlreadyActiveException extends RuntimeException {
}
