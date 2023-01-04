package web.clone.onemorebag.common.exception.form.item;

import web.clone.onemorebag.common.exception.form.CustomFormException;

public class EmptyFileException extends CustomFormException {
    public EmptyFileException(String message) {
        super(message);
    }

    public EmptyFileException(String message, String field) {
        super(message, field);
    }

    public EmptyFileException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
