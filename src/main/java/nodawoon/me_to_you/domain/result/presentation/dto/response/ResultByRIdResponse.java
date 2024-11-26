package nodawoon.me_to_you.domain.result.presentation.dto.response;

import nodawoon.me_to_you.domain.surveyResponse.domain.SurveyResponse;

import java.time.format.DateTimeFormatter;

public record ResultByRIdResponse(
        Long surveyQuestionId, String createdDate, String response
) {
    public ResultByRIdResponse(SurveyResponse surveyResponse) {
        this(
                surveyResponse.getSurveyQuestionId(),
                surveyResponse.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                surveyResponse.getResponse());
    }
}
