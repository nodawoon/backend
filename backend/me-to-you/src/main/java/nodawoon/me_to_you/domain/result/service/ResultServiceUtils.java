package nodawoon.me_to_you.domain.result.service;

import nodawoon.me_to_you.domain.result.presentation.dto.response.RespondentResponse;
import nodawoon.me_to_you.domain.result.presentation.dto.response.ResultByRIdResponse;

import java.util.List;

public interface ResultServiceUtils {
    List<RespondentResponse> getRespondentList(); // 설문 답변자 리스트 반환
    List<ResultByRIdResponse> getResultByRIDList(Long respondentId);// RespondentId별 결과 반환
    // surveyQuestionId별 결과 반환
    // 그래프용 결과 반환
}
