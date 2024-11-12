package nodawoon.me_to_you.domain.chatBot.domain.repository;

import nodawoon.me_to_you.domain.chatBot.domain.AnswerStatus;
import nodawoon.me_to_you.domain.chatBot.domain.ChatBot;
import nodawoon.me_to_you.domain.chatBot.presentation.dto.response.TargetUserWithLastChatBotResponse;
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

    @Query("SELECT new nodawoon.me_to_you.domain.chatBot.presentation.dto.response.TargetUserWithLastChatBotResponse(" +
            "c.targetUser.id, c.id, c.targetUser.nickname, c.targetUser.profileImageUrl, c.answer, c.answerStatus, c.isNew) " +
            "FROM ChatBot c " +
            "WHERE c.questionUser = :questionUser " +
            "AND c.id = (SELECT MAX(c2.id) FROM ChatBot c2 WHERE c2.questionUser = :questionUser AND c2.targetUser = c.targetUser) " +
            "ORDER BY c.lastModifyDate DESC")
    Slice<Object[]> findDistinctTargetUsersWithLastChatBot(@Param("questionUser") User questionUser, Pageable pageable);
}
