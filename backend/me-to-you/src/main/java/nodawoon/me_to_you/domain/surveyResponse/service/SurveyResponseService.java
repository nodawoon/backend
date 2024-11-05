package nodawoon.me_to_you.domain.surveyResponse.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodawoon.me_to_you.domain.result.domain.Respondent;
import nodawoon.me_to_you.domain.result.domain.repository.RespondentRepository;
import nodawoon.me_to_you.domain.surveyResponse.domain.SingleResponse;
import nodawoon.me_to_you.domain.surveyResponse.domain.SurveyResponse;
import nodawoon.me_to_you.domain.surveyResponse.domain.repository.SingleResponseRepository;
import nodawoon.me_to_you.domain.surveyResponse.domain.repository.SurveyResponseRepository;
import nodawoon.me_to_you.domain.surveyResponse.exception.UserNotFoundException;
import nodawoon.me_to_you.domain.surveyResponse.presentation.dto.request.SurveyResponseWrapperRequest;
import nodawoon.me_to_you.domain.surveyResponse.presentation.dto.request.SurveyResponseRequest;
import nodawoon.me_to_you.domain.user.domain.User;
import nodawoon.me_to_you.domain.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyResponseService {
    private final UserRepository userRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    private final RespondentRepository respondentRepository;
    private final SingleResponseRepository singleResponseRepository;

    @Transactional
    public void createSurveyResponse(SurveyResponseWrapperRequest wrapperRequest) {
        String uuid = wrapperRequest.shareUrl();
        User currentUser = userRepository.findByShareUrl(uuid).orElseThrow(()->UserNotFoundException.EXCEPTION);

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
                    respondent,
                    surveyResponseRequest.surveyQuestionId(), // 설문 아이디
                    responseString // response
            );

            // surveyResponse 생성
            surveyResponseRepository.save(surveyResponse);

            if(surveyResponse.getSurveyQuestionId() == 2){
                String[] splitResponses = surveyResponse.getResponse().split(",");

                for (String splitResponse : splitResponses) {
                    SingleResponse singleResponse = SingleResponse.createSingleResponse(
                            surveyResponse,
                            splitResponse.trim());
                    singleResponseRepository.save(singleResponse);
                }
            }
        }
    }
}
