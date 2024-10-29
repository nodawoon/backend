package nodawoon.me_to_you.domain.surveyResponse.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nodawoon.me_to_you.global.database.BaseEntity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class SurveyResponse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name ="survey_response_id")
    private Long id;

    private Long userId;
    // surveyQuestionId 를 Enum 으로 뺄 지, 일단은 안빼놓을게요.
    private Long surveyQuestionId;

    private String response;

    private String respondentNickname;
    private Long respondentId;




}
