package nodawoon.me_to_you.domain.surveyResponse.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nodawoon.me_to_you.domain.result.domain.Respondent;
import nodawoon.me_to_you.global.database.BaseEntity;

import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "respondent_id")
    private Respondent respondent;

    @OneToMany(mappedBy = "surveyResponse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SingleResponse> singleResponses = new ArrayList<>();

    private Long surveyQuestionId;

    private String response;

    @Builder
    public SurveyResponse(Respondent respondent, Long surveyQuestionId, String response) {
        this.respondent = respondent;
        this.surveyQuestionId = surveyQuestionId;
        this.response = response;
    }

    public static SurveyResponse createSurveyResponse(Respondent respondent, Long surveyQuestionId, String response) {
        return builder()
                .respondent(respondent)
                .surveyQuestionId(surveyQuestionId)
                .response(response)
                .build();
    }
}
