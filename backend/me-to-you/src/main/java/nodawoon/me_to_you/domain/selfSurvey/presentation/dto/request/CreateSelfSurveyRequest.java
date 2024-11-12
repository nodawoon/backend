package nodawoon.me_to_you.domain.selfSurvey.presentation.dto.request;

public record CreateSelfSurveyRequest(
        String question,
        String response
) {
}
