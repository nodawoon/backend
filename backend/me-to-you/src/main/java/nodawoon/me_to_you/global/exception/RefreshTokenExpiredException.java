package nodawoon.me_to_you.global.exception;

import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class RefreshTokenExpiredException extends MainException {

    public static final MainException EXCEPTION = new RefreshTokenExpiredException();

    private RefreshTokenExpiredException() {
        super(ErrorCode.REGISTER_EXPIRED_TOKEN);
    }
}
