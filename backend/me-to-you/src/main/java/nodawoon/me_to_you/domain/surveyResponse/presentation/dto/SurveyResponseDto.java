package nodawoon.me_to_you.domain.surveyResponse.presentation.dto;

import lombok.Data;

import java.time.LocalDateTime;

public record SurveyResponseDto(
        Long surveyResponseId,
        Long userId,
        Long surveyQuestionId,
        String response,  // 객관식, 주관식, 단답형 등 다양한 응답 형식이지만 다 json으로 받아옴
        LocalDateTime createdDate,
        LocalDateTime lastModifyDate,
        String respondentNickname,
        Long respondentId) {
}
