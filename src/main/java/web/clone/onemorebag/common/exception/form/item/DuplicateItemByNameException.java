package web.clone.onemorebag.common.exception.form.item;

import web.clone.onemorebag.common.exception.form.CustomFormException;

public class DuplicateItemByNameException extends CustomFormException {
    public DuplicateItemByNameException(String message) {
        super(message);
    }

    public DuplicateItemByNameException(String message, String field) {
        super(message, field);
    }

    public DuplicateItemByNameException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
