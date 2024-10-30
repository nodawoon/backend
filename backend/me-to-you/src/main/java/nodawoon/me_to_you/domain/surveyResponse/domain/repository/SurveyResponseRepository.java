package nodawoon.me_to_you.domain.surveyResponse.domain.repository;

import nodawoon.me_to_you.domain.surveyResponse.domain.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {
}
