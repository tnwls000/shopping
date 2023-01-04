package web.clone.onemorebag.common.exception.form.signup;

import web.clone.onemorebag.common.exception.form.CustomFormException;

public class InvalidMemberIdException extends CustomFormException {

    public InvalidMemberIdException(String message) {
        super(message);
    }

    public InvalidMemberIdException(String message, String field) {
        super(message, field);
    }

    public InvalidMemberIdException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
