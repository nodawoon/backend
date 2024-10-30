package nodawoon.me_to_you.domain.respondent.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nodawoon.me_to_you.domain.surveyResponse.domain.SurveyResponse;
import nodawoon.me_to_you.domain.user.domain.User;
import nodawoon.me_to_you.global.database.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "respondents")
public class Respondent extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "respondent_id")
    private Long id;

    private String respondentNickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 한 Respondent는 한 User에 속함

    @OneToMany(mappedBy = "respondent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyResponse> surveyResponses = new ArrayList<>();

    @Builder
    public Respondent(String respondentNickname, User user) {
        this.respondentNickname = respondentNickname;
        this.user = user;
    }

    public static Respondent createRespondent(String respondentNickname, User user){
        return builder()
                .respondentNickname(respondentNickname)
                .user(user)
                .build();
    }
}
