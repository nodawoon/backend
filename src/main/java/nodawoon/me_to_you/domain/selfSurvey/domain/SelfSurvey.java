package nodawoon.me_to_you.domain.selfSurvey.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nodawoon.me_to_you.domain.selfSurvey.exception.UserNotSelfSurveyHostException;
import nodawoon.me_to_you.domain.user.domain.User;
import nodawoon.me_to_you.global.database.BaseEntity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class SelfSurvey extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "self_survey_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 200)
    private String question;

    @Column(length = 600)
    private String answer;

    @Builder
    public SelfSurvey(User user, String question, String answer) {
        this.user = user;
        this.question = question;
        this.answer = answer;
    }

    public static SelfSurvey createSelfSurvey(User user, String question, String answer) {
        return builder()
                .user(user)
                .question(question)
                .answer(answer)
                .build();
    }

    public void validUserHost(User user) {
        if(!this.user.equals(user)) {
            throw UserNotSelfSurveyHostException.EXCEPTION;
        }
    }

    public void changeAnswer(String answer) {
        this.answer = answer;
    }
}
