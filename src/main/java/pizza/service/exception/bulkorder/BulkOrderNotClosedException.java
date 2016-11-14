package pizza.service.exception.bulkorder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Keissenator on 13.11.2016.
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Another buld order is currently not finished!")
public class BulkOrderNotClosedException extends RuntimeException {
}
