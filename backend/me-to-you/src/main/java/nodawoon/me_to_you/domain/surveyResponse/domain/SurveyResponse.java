package nodawoon.me_to_you.domain.surveyResponse.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    // surveyQuestionId 를 Enum 으로 뺄 지, 일단은 안빼놓을게요.
    private Long surveyQuestionId;

    private String response;

    private String respondentNickname;
    private Long respondentId;

    @Builder
    public SurveyResponse(User user, Long surveyQuestionId, String response, String respondentNickname, Long respondentId) {
        this.user = user;
        this.surveyQuestionId = surveyQuestionId;
        this.response = response;
        this.respondentNickname = respondentNickname;
        this.respondentId = respondentId;
    }

    public static SurveyResponse createSurveyResponse(User user, Long surveyQuestionId, String response, String respondentNickname, Long respondentId) {
        return builder()
                .user(user)
                .surveyQuestionId(surveyQuestionId)
                .response(response)
                .respondentNickname(respondentNickname)
                .respondentId(respondentId)
                .build();
    }
}
