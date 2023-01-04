package web.clone.onemorebag.common.exception.form.order;

import web.clone.onemorebag.common.exception.form.CustomFormException;

public class AlreadyCompDeliveryException extends CustomFormException {
    public AlreadyCompDeliveryException(String message) {
        super(message);
    }

    public AlreadyCompDeliveryException(String message, String field) {
        super(message, field);
    }

    public AlreadyCompDeliveryException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
