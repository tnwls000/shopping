package web.clone.onemorebag.common.exception.form.order;

import web.clone.onemorebag.common.exception.form.CustomFormException;

public class NotEnoughStockQuantity extends CustomFormException {
    public NotEnoughStockQuantity(String message) {
        super(message);
    }

    public NotEnoughStockQuantity(String message, String field) {
        super(message, field);
    }

    public NotEnoughStockQuantity(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
