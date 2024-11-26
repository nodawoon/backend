package nodawoon.me_to_you.domain.user.exception;


import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class NicknameDuplicationException extends MainException {

    public static final MainException EXCEPTION = new NicknameDuplicationException();

    private NicknameDuplicationException() {
        super(ErrorCode.NICKNAME_DUPLICATION);
    }
}
