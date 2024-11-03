package nodawoon.me_to_you.domain.result.presentation.dto.response;

public record ResultByPercentResponse(
        String surveyQuestionId, String response, Double percent) {
    public ResultByPercentResponse(Object[] result) {
        this((String) result[0], (String) result[1], (Double) result[2]);
    }
}
