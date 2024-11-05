package nodawoon.me_to_you.domain.surveyResponse.domain.repository;

import nodawoon.me_to_you.domain.surveyResponse.domain.SingleResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingleResponseRepository extends JpaRepository<SingleResponse, Long> {
}
