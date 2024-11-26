package nodawoon.me_to_you.domain.selfSurvey.domain.repository;

import nodawoon.me_to_you.domain.selfSurvey.domain.SelfSurvey;
import nodawoon.me_to_you.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelfSurveyRepository extends JpaRepository<SelfSurvey, Long> {
    List<SelfSurvey> findAllByUser(User user);

    boolean existsByUser(User user);

    void deleteAllByUser(User user);
}
