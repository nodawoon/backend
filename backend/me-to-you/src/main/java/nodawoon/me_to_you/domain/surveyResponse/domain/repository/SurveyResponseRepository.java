package nodawoon.me_to_you.domain.surveyResponse.domain.repository;

import nodawoon.me_to_you.domain.result.domain.Respondent;
import nodawoon.me_to_you.domain.surveyResponse.domain.SurveyResponse;
import nodawoon.me_to_you.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {
    List<SurveyResponse> findByRespondentAndUserOrderBySurveyQuestionIdAsc(Respondent respondent, User user);
    List<SurveyResponse> findBySurveyQuestionIdAndUserOrderById(Long surveyQuestionId, User user);
}
