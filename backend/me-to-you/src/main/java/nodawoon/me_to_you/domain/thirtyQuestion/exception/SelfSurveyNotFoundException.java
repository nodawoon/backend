package nodawoon.me_to_you.domain.thirtyQuestion.exception;

import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class SelfSurveyNotFoundException extends MainException {
    public static final MainException EXCEPTION = new SelfSurveyNotFoundException();

    private SelfSurveyNotFoundException() {
        super(ErrorCode.SELF_SURVEY_NOT_FOUND);
    }
}
