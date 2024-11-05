package nodawoon.me_to_you.domain.thirtyQuestion.exception;

import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class UserNotSelfSurveyHostException extends MainException {
    public static final MainException EXCEPTION = new UserNotSelfSurveyHostException();

    private UserNotSelfSurveyHostException() {
        super(ErrorCode.USER_NOT_SELF_SURVEY_HOST);
    }
}
