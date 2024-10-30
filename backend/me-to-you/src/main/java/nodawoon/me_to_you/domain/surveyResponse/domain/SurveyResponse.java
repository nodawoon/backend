package nodawoon.me_to_you.domain.surveyResponse.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nodawoon.me_to_you.domain.respondent.domain.Respondent;
import nodawoon.me_to_you.domain.user.domain.User;
import nodawoon.me_to_you.global.database.BaseEntity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "survey_responses")
public class SurveyResponse extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name ="survey_response_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "respondent_id")
    private Respondent respondent;

    private Long surveyQuestionId;

    private String response;

    @Builder
    public SurveyResponse(User user, Respondent respondent, Long surveyQuestionId, String response) {
        this.user = user;
        this.respondent = respondent;
        this.surveyQuestionId = surveyQuestionId;
        this.response = response;
    }

    public static SurveyResponse createSurveyResponse(User user, Respondent respondent, Long surveyQuestionId, String response) {
        return builder()
                .user(user)
                .respondent(respondent)
                .surveyQuestionId(surveyQuestionId)
                .response(response)
                .build();
    }
}
