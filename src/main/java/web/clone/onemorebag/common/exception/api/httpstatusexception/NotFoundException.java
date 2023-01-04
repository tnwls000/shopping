package web.clone.onemorebag.common.exception.api.httpstatusexception;

import org.springframework.http.HttpStatus;
import web.clone.onemorebag.common.exception.api.CustomApiException;

public class NotFoundException extends CustomApiException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
