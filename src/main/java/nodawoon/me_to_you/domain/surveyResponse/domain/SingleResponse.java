package nodawoon.me_to_you.domain.surveyResponse.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nodawoon.me_to_you.global.database.BaseEntity;

import static jakarta.persistence.GenerationType.IDENTITY;


import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "single_responses")
public class SingleResponse extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name="single_response_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_response_id")
    private SurveyResponse surveyResponse;

    private String responseDetail;

    @Builder
    public SingleResponse(SurveyResponse surveyResponse, String responseDetail) {
        this.surveyResponse = surveyResponse;
        this.responseDetail = responseDetail;
    }

    public static SingleResponse createSingleResponse(SurveyResponse surveyResponse, String responseDetail) {
        return builder()
                .surveyResponse(surveyResponse)
                .responseDetail(responseDetail)
                .build();
    }
}
