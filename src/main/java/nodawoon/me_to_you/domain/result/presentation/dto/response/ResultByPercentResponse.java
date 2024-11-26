package nodawoon.me_to_you.domain.result.presentation.dto.response;

import java.math.BigDecimal;

public record ResultByPercentResponse(
        Long surveyQuestionId, String response, Double percent) {
    public ResultByPercentResponse(Object[] result) {
        this(
                (Long) result[0],
                (String) result[1],
                result[2] != null ? ((BigDecimal) result[2]).doubleValue() : 0.0);
    }
}
