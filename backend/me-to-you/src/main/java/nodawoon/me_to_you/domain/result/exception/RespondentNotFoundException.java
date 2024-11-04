package nodawoon.me_to_you.domain.result.exception;

import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class RespondentNotFoundException extends MainException {
    public static final MainException EXCEPTION = new RespondentNotFoundException();

    private RespondentNotFoundException(){
        super(ErrorCode.RESPONDENT_NOT_FOUND);
    }
}
