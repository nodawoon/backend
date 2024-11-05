package nodawoon.me_to_you.domain.result.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodawoon.me_to_you.domain.result.domain.Respondent;
import nodawoon.me_to_you.domain.result.domain.repository.RespondentRepository;
import nodawoon.me_to_you.domain.result.presentation.dto.response.*;
import nodawoon.me_to_you.domain.surveyResponse.domain.SurveyResponse;
import nodawoon.me_to_you.domain.surveyResponse.domain.repository.SingleResponseRepository;
import nodawoon.me_to_you.domain.surveyResponse.domain.repository.SurveyResponseRepository;
import nodawoon.me_to_you.domain.user.domain.User;
import nodawoon.me_to_you.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResultService implements ResultServiceUtils {
    private final RespondentRepository respondentRepository;
    private final SurveyResponseRepository surveyResponseRepository;

    private final UserUtils userUtils;
    private final SingleResponseRepository singleResponseRepository;

    @Override
    public List<RespondentResponse> getRespondentList() {
        User currentUser = userUtils.getUserFromSecurityContext();

        List<Respondent> respondentList = respondentRepository.findByUser(currentUser);

        // RespondentResponse로 변환
        return respondentList.stream()
                .map(RespondentResponse::new)
                .toList();
    }

    @Override
    public List<ResultByRIdResponse> getResultByRIDList(Long respondentId) {
        Respondent respondent = respondentRepository.findById(respondentId).orElse(null);

        List<SurveyResponse> surveyResponseList = surveyResponseRepository.findByRespondentOrderBySurveyQuestionIdAsc(respondent);

        return surveyResponseList.stream()
                .map(ResultByRIdResponse::new)
                .toList();
    }

        @Override
    public List<ResultByQIdResponse> getResultByQIdList(Long surveyQuestionId) {
        User currentUser = userUtils.getUserFromSecurityContext();

        List<SurveyResponse> surveyResponseList = surveyResponseRepository.findByUserAndSurveyQuestionIdOrderById(currentUser, surveyQuestionId);

        return surveyResponseList.stream()
                .map(surveyResponse -> new ResultByQIdResponse(
                        getRespondentNickname(surveyResponse.getRespondent()),
                        surveyResponse))
                .toList();
    }

    @Override
    public List<ResultByPercentResponse> getResultByPercentList() {
        User currentUser = userUtils.getUserFromSecurityContext();
        Long userId = currentUser.getId();

        List<Object[]> getPercentResponses = surveyResponseRepository.percentResponsesForQuestions(userId);

        return getPercentResponses.stream()
                .map(ResultByPercentResponse::new)
                .toList();
    }

    @Override
    public List<ResultByCountResponse> getResultByCountList() {
        User currentUser = userUtils.getUserFromSecurityContext();
        Long userId = currentUser.getId();

        List<Object[]> getCountResponses = singleResponseRepository.findTop3ResponseDetailsByUser(userId);

        return getCountResponses.stream()
                .map(ResultByCountResponse::new)
                .toList();
    }

    public String getRespondentNickname(Respondent respondent) {
        Long respondentId = respondent.getId();
        return respondentRepository.findById(respondentId)
                .map(Respondent::getRespondentNickname)
                .orElse("UnKnown");
    }

}
