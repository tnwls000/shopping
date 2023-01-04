package web.clone.onemorebag.common.exception.form.item;

import web.clone.onemorebag.common.exception.form.CustomFormException;

public class DuplicateItemByIdException extends CustomFormException {
    public DuplicateItemByIdException(String message) {
        super(message);
    }

    public DuplicateItemByIdException(String message, String field) {
        super(message, field);
    }

    public DuplicateItemByIdException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
