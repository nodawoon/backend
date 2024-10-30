package nodawoon.me_to_you.domain.result.domain.repository;

import nodawoon.me_to_you.domain.result.domain.Respondent;
import nodawoon.me_to_you.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespondentRepository extends JpaRepository<Respondent, Long> {
    List<Respondent> findByUser(User user);
}
