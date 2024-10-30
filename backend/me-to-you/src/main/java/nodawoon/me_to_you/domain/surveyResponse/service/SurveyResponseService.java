package nodawoon.me_to_you.domain.surveyResponse.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodawoon.me_to_you.domain.surveyResponse.domain.SurveyResponse;
import nodawoon.me_to_you.domain.surveyResponse.domain.repository.SurveyResponseRepository;
import nodawoon.me_to_you.domain.surveyResponse.presentation.dto.request.SurveyResponseRequest;
import nodawoon.me_to_you.domain.user.domain.User;
import nodawoon.me_to_you.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyResponseService {
    private final SurveyResponseRepository surveyResponseRepository;
    private final UserUtils userUtils;

    @Transactional
    public void createSurveyResponse(SurveyResponseRequest surveyResponseRequest) {
        User currentUser = userUtils.getUserFromSecurityContext();

        SurveyResponse surveyResponse = SurveyResponse.createSurveyResponse(
                currentUser,
                surveyResponseRequest.surveyResponseId(),
                surveyResponseRequest.response(),
                surveyResponseRequest.respondentNickname(),
                surveyResponseRequest.respondentId()
        );

        surveyResponseRepository.save(surveyResponse);
    }
}
