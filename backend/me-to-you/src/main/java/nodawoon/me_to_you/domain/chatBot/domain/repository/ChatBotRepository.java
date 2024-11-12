package nodawoon.me_to_you.domain.chatBot.domain.repository;

import nodawoon.me_to_you.domain.chatBot.domain.AnswerStatus;
import nodawoon.me_to_you.domain.chatBot.domain.ChatBot;
import nodawoon.me_to_you.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatBotRepository extends JpaRepository<ChatBot, Long> {
    List<ChatBot> findByTargetUserAndIsQuestionIncludedTrue(User targetUser);

    Slice<ChatBot> findAllByTargetUserAndAnswerStatus(User targetUser, AnswerStatus answerStatus, Pageable pageable);

    Slice<ChatBot> findAllByTargetUserAndQuestionUser(User targetUser, User questionUser, Pageable pageable);

    @Query("SELECT c.targetUser FROM ChatBot c WHERE c.questionUser = :questionUser GROUP BY c.targetUser, c.id ORDER BY c.id DESC")
    Slice<User> findDistinctTargetUsersByQuestionUser(@Param("questionUser") User questionUser, Pageable pageable);
}
