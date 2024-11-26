package nodawoon.me_to_you.global.exception;


import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class ExpiredTokenException extends MainException {

    public static final MainException EXCEPTION = new ExpiredTokenException();

    private ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
