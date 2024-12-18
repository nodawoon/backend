package nodawoon.me_to_you.domain.selfSurvey.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodawoon.me_to_you.domain.selfSurvey.domain.SelfSurvey;
import nodawoon.me_to_you.domain.selfSurvey.domain.repository.SelfSurveyRepository;
import nodawoon.me_to_you.domain.selfSurvey.exception.SelfSurveyNotFoundException;
import nodawoon.me_to_you.domain.selfSurvey.presentation.dto.request.CreateSelfSurveyRequest;
import nodawoon.me_to_you.domain.selfSurvey.presentation.dto.request.UpdateSelfSurveyRequest;
import nodawoon.me_to_you.domain.selfSurvey.presentation.dto.response.SelfSurveyResponse;
import nodawoon.me_to_you.domain.selfSurvey.presentation.dto.response.UserSelfSurveyStatusResponse;
import nodawoon.me_to_you.domain.user.domain.User;
import nodawoon.me_to_you.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SelfSurveyService implements SelfSurveyServiceUtils{

    private final SelfSurveyRepository selfSurveyRepository;
    private final UserUtils userUtils;

    // 질문 저장하기
    @Transactional
    public List<SelfSurveyResponse> createSelfSurvey(List<CreateSelfSurveyRequest> createSelfSurveyRequests) {
        User user = userUtils.getUserFromSecurityContext();

        selfSurveyRepository.deleteAllByUser(user);

        List<SelfSurveyResponse> responses = createSelfSurveyRequests.stream()
                .map(request -> {
                    SelfSurvey selfSurvey = SelfSurvey.createSelfSurvey(
                            user,
                            request.question(),
                            request.response()
                    );
                    selfSurveyRepository.save(selfSurvey);
                    return new SelfSurveyResponse(selfSurvey);
                })
                .collect(Collectors.toList());

        return responses;
    }


    // 질문 리스트 조회하기
    public List<SelfSurveyResponse> getSelfSurveys() {
        User user = userUtils.getUserFromSecurityContext();
        List<SelfSurvey> allByUser = getSelfSurveysByUser(user);

        return allByUser.stream()
                .map(SelfSurveyResponse::new)
                .collect(Collectors.toList());
    }
    
    // 해당 질문 수정하기
    @Transactional
    public SelfSurveyResponse updateSelfSurvey(Long thirtyQuestionId, UpdateSelfSurveyRequest updateSelfSurveyRequest) {
        User user = userUtils.getUserFromSecurityContext();
        SelfSurvey selfSurvey = querySelfSurveyById(thirtyQuestionId);

        selfSurvey.validUserHost(user);

        selfSurvey.changeAnswer(updateSelfSurveyRequest.response());

        return new SelfSurveyResponse(selfSurvey);
    }
    
    // 30문 30답 유무 조회
    public UserSelfSurveyStatusResponse checkUserSelfSurveyStatus(Long userId) {
        User user = userUtils.getUserById(userId);
        boolean exists = selfSurveyRepository.existsByUser(user);

        return new UserSelfSurveyStatusResponse(exists);
    }

    @Override
    public SelfSurvey querySelfSurveyById(Long id) {
        return selfSurveyRepository.findById(id).orElseThrow(() -> SelfSurveyNotFoundException.EXCEPTION);
    }

    @Override
    public List<SelfSurvey> getSelfSurveysByUser(User user) {
        return selfSurveyRepository.findAllByUser(user);
    }
}
