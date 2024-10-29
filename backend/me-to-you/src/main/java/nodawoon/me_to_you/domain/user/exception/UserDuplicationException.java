package nodawoon.me_to_you.domain.user.exception;


import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class UserDuplicationException extends MainException {

    public static final MainException EXCEPTION = new UserDuplicationException();

    private UserDuplicationException() {
        super(ErrorCode.USER_DUPLICATION);
    }
}
