package web.clone.onemorebag.common.exception.api.httpstatusexception;

import org.springframework.http.HttpStatus;
import web.clone.onemorebag.common.exception.api.CustomApiException;

public class BadRequestException extends CustomApiException {

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
