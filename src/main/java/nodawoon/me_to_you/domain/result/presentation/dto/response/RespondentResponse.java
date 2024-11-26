package nodawoon.me_to_you.domain.result.presentation.dto.response;

import nodawoon.me_to_you.domain.result.domain.Respondent;

public record RespondentResponse(Long respondentId, String respondentNickname) {
    public RespondentResponse(Respondent respondent){
        this(respondent.getId(), respondent.getRespondentNickname());
    }
}
