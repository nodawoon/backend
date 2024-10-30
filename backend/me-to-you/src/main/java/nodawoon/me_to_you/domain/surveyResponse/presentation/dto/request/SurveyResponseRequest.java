package nodawoon.me_to_you.domain.surveyResponse.presentation.dto.request;

import java.time.LocalDateTime;
import java.util.List;

public record SurveyResponseRequest(
        Long surveyResponseId,
        Long userId,
        Long surveyQuestionId,
        List<String> response,  // 객관식, 주관식, 단답형 등 다양한 응답 형식이지만 다 json으로 받아옴
        LocalDateTime createdDate,
        LocalDateTime lastModifyDate,
        String respondentNickname,
        String respondentId) {
}
