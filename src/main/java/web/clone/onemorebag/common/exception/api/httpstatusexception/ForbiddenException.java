package web.clone.onemorebag.common.exception.api.httpstatusexception;

import org.springframework.http.HttpStatus;
import web.clone.onemorebag.common.exception.api.CustomApiException;

public class ForbiddenException extends CustomApiException {
    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
