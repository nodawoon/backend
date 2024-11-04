package nodawoon.me_to_you.domain.result.presentation.dto.response;

import nodawoon.me_to_you.domain.surveyResponse.domain.SurveyResponse;

public record ResultByRIdResponse(
        Long surveyQuestionId, String response
) {
    public ResultByRIdResponse(SurveyResponse surveyResponse) {
        this(surveyResponse.getSurveyQuestionId(), surveyResponse.getResponse());
    }
}
