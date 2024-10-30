package nodawoon.me_to_you.domain.result.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodawoon.me_to_you.domain.result.domain.Respondent;
import nodawoon.me_to_you.domain.result.domain.repository.RespondentRepository;
import nodawoon.me_to_you.domain.result.presentation.dto.response.RespondentResponse;
import nodawoon.me_to_you.domain.user.domain.User;
import nodawoon.me_to_you.global.utils.user.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResultService {

    private final RespondentRepository respondentRepository;
    private final UserUtils userUtils;

    public List<RespondentResponse> getRespondentList() {
        User currentUser = userUtils.getUserFromSecurityContext();

        List<Respondent> respondentList = respondentRepository.findByUser(currentUser);

        // RespondentDto로 변환
        return respondentList.stream()
                .map(respondent -> new RespondentResponse(respondent.getId(), respondent.getRespondentNickname()))
                .toList();
    }
}
