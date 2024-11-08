package nodawoon.me_to_you.domain.selfSurvey.presentation.dto.response;

import nodawoon.me_to_you.domain.selfSurvey.domain.SelfSurvey;

import java.time.LocalDateTime;

public record SelfSurveyResponse(
        Long id,
        String question,
        String answer,
        LocalDateTime createdAt,
        LocalDateTime lastModifyDate
) {
    public SelfSurveyResponse(SelfSurvey selfSurvey) {
        this(selfSurvey.getId(), selfSurvey.getQuestion(), selfSurvey.getAnswer(),
                selfSurvey.getCreatedDate(), selfSurvey.getLastModifyDate());
    }
}
