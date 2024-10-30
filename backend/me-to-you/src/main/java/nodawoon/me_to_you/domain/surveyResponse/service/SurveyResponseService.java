package nodawoon.me_to_you.domain.surveyResponse.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodawoon.me_to_you.domain.result.domain.Respondent;
import nodawoon.me_to_you.domain.result.domain.repository.RespondentRepository;
import nodawoon.me_to_you.domain.surveyResponse.domain.SurveyResponse;
import nodawoon.me_to_you.domain.surveyResponse.domain.repository.SurveyResponseRepository;
import nodawoon.me_to_you.domain.surveyResponse.presentation.dto.request.SurveyResponseWrapperRequest;
import nodawoon.me_to_you.domain.surveyResponse.presentation.dto.request.SurveyResponseRequest;
import nodawoon.me_to_you.domain.user.domain.User;
import nodawoon.me_to_you.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyResponseService {
    private final SurveyResponseRepository surveyResponseRepository;
    private final RespondentRepository respondentRepository;
    private final UserUtils userUtils;

    @Transactional
    public void createSurveyResponse(SurveyResponseWrapperRequest wrapperRequest) {
        User currentUser = userUtils.getUserFromSecurityContext();

        // Respondent 생성
        String respondentNickname = wrapperRequest.respondentNickname();
        Respondent respondent = Respondent.createRespondent(
                respondentNickname,
                currentUser
                );

        respondentRepository.save(respondent);

        List<SurveyResponseRequest> surveyResponseRequestList= wrapperRequest.surveyResponseRequestList();

        for (SurveyResponseRequest surveyResponseRequest : surveyResponseRequestList) {
            String responseString = String.join(",", surveyResponseRequest.response()); // 다수의 응답 변환

            SurveyResponse surveyResponse = SurveyResponse.createSurveyResponse(
                    currentUser,
                    respondent,
                    surveyResponseRequest.surveyQuestionId(), // 설문 아이디
                    responseString // response
            );

            surveyResponseRepository.save(surveyResponse);
        }
    }
}
