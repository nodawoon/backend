package nodawoon.me_to_you.domain.chatBot.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nodawoon.me_to_you.domain.chatBot.exception.LimitCountExceededException;
import nodawoon.me_to_you.domain.chatBot.exception.QuestionUserNotHostChatBotException;
import nodawoon.me_to_you.domain.chatBot.exception.TargetUserNotHostChatBotException;
import nodawoon.me_to_you.domain.user.domain.User;
import nodawoon.me_to_you.global.database.BaseEntity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ChatBot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "chatbot_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_user_id")
    private User questionUser;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "target_user_id")
    private User targetUser;

    @Column(length = 500)
    private String question;

    @Column(length = 500)
    private String answer;
    private boolean isQuestionIncluded;
    private int limitCount;
    private boolean isNew;

    @Enumerated(EnumType.STRING)
    private AnswerStatus answerStatus;

    @Builder
    public ChatBot(User questionUser, User targetUser, String question, String answer, boolean isQuestionIncluded, int limitCount, boolean isNew, AnswerStatus answerStatus) {
        this.questionUser = questionUser;
        this.targetUser = targetUser;
        this.question = question;
        this.answer = answer;
        this.isQuestionIncluded = isQuestionIncluded;
        this.limitCount = limitCount;
        this.isNew = isNew;
        this.answerStatus = answerStatus;
    }

    public static ChatBot createChatBot(User questionUser, User targetUser, String question, String answer, AnswerStatus answerStatus) {
        return builder()
                .questionUser(questionUser)
                .targetUser(targetUser)
                .question(question)
                .answer(answer)
                .isQuestionIncluded(false)
                .limitCount(3)
                .isNew(false)
                .answerStatus(answerStatus)
                .build();
    }

    public void validTargetUserHost(User user) {
        if(!this.targetUser.equals(user)) {
            throw TargetUserNotHostChatBotException.EXCEPTION;
        }
    }

    public void validQuestionUserHost(User user) {
        if(!this.questionUser.equals(user)) {
            throw QuestionUserNotHostChatBotException.EXCEPTION;
        }
    }

    public void userAnswer(String answer) {
        this.answer = answer;
        this.answerStatus = AnswerStatus.ANSWERED_BY_USER;
        this.isNew = true;
    }

    public void readChatBot() {
        this.isNew = false;
    }

    public void addChatBotPrompt() {
        this.isQuestionIncluded = true;
    }

    public void removeChatBotPrompt() {
        this.isQuestionIncluded = false;
    }

    public void requestChatBot() {
        if (this.limitCount <= 0) {
            throw LimitCountExceededException.EXCEPTION;
        }
    }

    public void subLimitCount() {
        this.limitCount--;
    }

    public void updateAnswer(String answer) {
        this.answer = answer;
    }

    public void updateAnswerStatus(AnswerStatus answerStatus) {
        this.answerStatus = answerStatus;
    }
}
