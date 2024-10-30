package nodawoon.me_to_you.domain.surveyResponse.presentation.dto.request;

import java.util.List;

public record SurveyResponseWrapperRequest(
        String respondentNickname,
        List<SurveyResponseRequest> surveyResponseRequestList) {
}
