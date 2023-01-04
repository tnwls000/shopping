package web.clone.onemorebag.common.exception.form.signup;

import web.clone.onemorebag.common.exception.form.CustomFormException;

public class DuplicateMemberIdException extends CustomFormException {
    public DuplicateMemberIdException(String message) {
        super(message);
    }

    public DuplicateMemberIdException(String message, String field) {
        super(message, field);
    }

    public DuplicateMemberIdException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
