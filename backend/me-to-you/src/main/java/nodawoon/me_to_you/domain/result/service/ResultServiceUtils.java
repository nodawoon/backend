package nodawoon.me_to_you.domain.result.service;

import nodawoon.me_to_you.domain.result.presentation.dto.response.*;
import java.util.List;

public interface ResultServiceUtils {
    List<RespondentResponse> getRespondentList(); // 설문 답변자 리스트 반환
    List<ResultByRIdResponse> getResultByRIDList(Long respondentId);// 응답자(RespondentId)별 결과 반환
    List<ResultByQIdResponse> getResultByQIdList(Long surveyQuestionId);// 질문 번호(surveyQuestionId)별 결과 반환
    List<ResultByPercentResponse> getResultByPercentList();// percent 결과 반환
    List<ResultByCountResponse> getResultByCountList(); // count 반환
}
