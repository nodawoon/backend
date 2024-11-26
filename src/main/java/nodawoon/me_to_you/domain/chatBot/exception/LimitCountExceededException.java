package nodawoon.me_to_you.domain.chatBot.exception;

import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class LimitCountExceededException extends MainException {
    public static final MainException EXCEPTION = new LimitCountExceededException();

    private LimitCountExceededException() {
        super(ErrorCode.LIMIT_COUNT_EXCEEDED);
    }
}
