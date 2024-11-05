package nodawoon.me_to_you.domain.thirtyQuestion.presentation.dto.request;

public record CreateSelfSurveyRequest(
        String question,
        String response
) {
}
