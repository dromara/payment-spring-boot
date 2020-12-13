package cn.felord.payment;

/**
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public class PayException extends RuntimeException {

    public PayException() {
    }

    public PayException(String message) {
        super(message);
    }

    public PayException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayException(Throwable cause) {
        super(cause);
    }

    public PayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
