package nodawoon.me_to_you.domain.surveyResponse.presentation.dto.request;

import java.util.List;

public record SurveyResponseRequest(
        Long surveyQuestionId,
        List<String> response) {
}
