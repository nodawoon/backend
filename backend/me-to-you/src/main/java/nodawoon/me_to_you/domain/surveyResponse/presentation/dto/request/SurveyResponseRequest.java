package nodawoon.me_to_you.domain.surveyResponse.presentation.dto.request;

import java.util.List;

public record SurveyResponseRequest(
        Long surveyQuestionId,
        List<String> response // 객관식, 주관식, 단답형 등 다양한 응답 형식이지만 다 json으로 받아옴
        ) {
}
