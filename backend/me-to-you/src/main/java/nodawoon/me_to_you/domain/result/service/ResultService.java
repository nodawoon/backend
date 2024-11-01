package nodawoon.me_to_you.domain.result.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodawoon.me_to_you.domain.result.domain.Respondent;
import nodawoon.me_to_you.domain.result.domain.repository.RespondentRepository;
import nodawoon.me_to_you.domain.result.presentation.dto.response.RespondentResponse;
import nodawoon.me_to_you.domain.result.presentation.dto.response.ResultByQIdResponse;
import nodawoon.me_to_you.domain.result.presentation.dto.response.ResultByRIdResponse;
import nodawoon.me_to_you.domain.surveyResponse.domain.SurveyResponse;
import nodawoon.me_to_you.domain.surveyResponse.domain.repository.SurveyResponseRepository;
import nodawoon.me_to_you.domain.user.domain.User;
import nodawoon.me_to_you.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResultService implements ResultServiceUtils {
    private final RespondentRepository respondentRepository;
    private final SurveyResponseRepository surveyResponseRepository;

    private final UserUtils userUtils;

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
        User currentUser = userUtils.getUserFromSecurityContext();
        Respondent respondent = respondentRepository.findById(respondentId).orElse(null);

        List<SurveyResponse> SurveyResponseList = surveyResponseRepository.findByRespondentAndUserOrderBySurveyQuestionIdAsc(respondent, currentUser);

        return SurveyResponseList.stream()
                .map(ResultByRIdResponse::new)
                .toList();
    }

    @Override
    public List<ResultByQIdResponse> getResultByQIdList(Long surveyQuestionId) {
        User currentUser = userUtils.getUserFromSecurityContext();

        List<SurveyResponse> SurveyResponseList = surveyResponseRepository.findBySurveyQuestionIdAndUserOrderById(surveyQuestionId, currentUser);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        return SurveyResponseList.stream()
                .map(surveyResponse -> new ResultByQIdResponse(
                        getRespondentNickname(surveyResponse.getRespondent()),
                        surveyResponse.getCreatedDate().format(formatter),
                        surveyResponse.getResponse()))
                .toList();
    }

    public String getRespondentNickname(Respondent respondent) {
        Long respondentId = respondent.getId();
        return respondentRepository.findById(respondentId)
                .map(Respondent::getRespondentNickname)
                .orElse("UnKnown");
    }

}
