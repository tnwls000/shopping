package web.clone.onemorebag.common.exception.form.signup;

import web.clone.onemorebag.common.exception.form.CustomFormException;

public class InvalidPasswordException extends CustomFormException {
    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, String field) {
        super(message, field);
    }

    public InvalidPasswordException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
