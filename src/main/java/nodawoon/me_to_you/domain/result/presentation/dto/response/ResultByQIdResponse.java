package nodawoon.me_to_you.domain.result.presentation.dto.response;

import nodawoon.me_to_you.domain.surveyResponse.domain.SurveyResponse;

import java.time.format.DateTimeFormatter;

public record ResultByQIdResponse(String respondentNickname, String createdDate, String response) {
    public ResultByQIdResponse(String respondentNickname, SurveyResponse surveyResponse) {
        this(
                respondentNickname,
                surveyResponse.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                surveyResponse.getResponse());
    }
}
