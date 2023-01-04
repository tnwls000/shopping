package web.clone.onemorebag.common.exception.api.httpstatusexception;

import org.springframework.http.HttpStatus;
import web.clone.onemorebag.common.exception.api.CustomApiException;

public class UnauthorizedException extends CustomApiException {
    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
