package web.clone.onemorebag.common.exception.form.order;

import web.clone.onemorebag.common.exception.form.CustomFormException;

public class AlreadyCancelOrderException extends CustomFormException {
    public AlreadyCancelOrderException(String message) {
        super(message);
    }

    public AlreadyCancelOrderException(String message, String field) {
        super(message, field);
    }

    public AlreadyCancelOrderException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
