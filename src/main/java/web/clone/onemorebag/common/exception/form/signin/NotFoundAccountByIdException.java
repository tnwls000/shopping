package web.clone.onemorebag.common.exception.form.signin;

import web.clone.onemorebag.common.exception.form.CustomFormException;

public class NotFoundAccountByIdException extends CustomFormException {
    public NotFoundAccountByIdException(String message) {
        super(message);
    }

    public NotFoundAccountByIdException(String message, String field) {
        super(message, field);
    }

    public NotFoundAccountByIdException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
