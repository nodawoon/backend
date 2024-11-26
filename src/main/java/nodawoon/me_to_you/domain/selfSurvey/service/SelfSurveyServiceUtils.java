package nodawoon.me_to_you.domain.selfSurvey.service;

import nodawoon.me_to_you.domain.selfSurvey.domain.SelfSurvey;
import nodawoon.me_to_you.domain.user.domain.User;

import java.util.List;

public interface SelfSurveyServiceUtils {
    SelfSurvey querySelfSurveyById(Long id);

    List<SelfSurvey> getSelfSurveysByUser(User user);
}
