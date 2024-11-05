package nodawoon.me_to_you.domain.surveyResponse.presentation.dto.request;

public record SurveyResponseRequest(
        Long surveyQuestionId,
        String response
        ) {
}
