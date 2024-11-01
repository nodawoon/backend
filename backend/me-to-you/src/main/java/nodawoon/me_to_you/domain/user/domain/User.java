package nodawoon.me_to_you.domain.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nodawoon.me_to_you.domain.oauth.domain.OauthServerType;
import nodawoon.me_to_you.domain.result.domain.Respondent;
import nodawoon.me_to_you.domain.surveyResponse.domain.SurveyResponse;
import nodawoon.me_to_you.global.database.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname;
    private String email;
    private String profileImageUrl;
    private LocalDate birthday;
    private String shareUrl;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyResponse> surveyResponses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respondent> respondents = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Mbti mbti;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private OauthServerType oauthServerType;

    @Builder
    public User(String nickname, String email, String profileImageUrl, LocalDate birthday, String shareUrl, Mbti mbti, Gender gender, OauthServerType oauthServerType) {
        this.nickname = nickname;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.birthday = birthday;
        this.shareUrl = shareUrl;
        this.mbti = mbti;
        this.gender = gender;
        this.oauthServerType = oauthServerType;
    }

    public static User createUser(String nickname, String email, String profileImageUrl, LocalDate birthday, String shareUrl, Mbti mbti, Gender gender, OauthServerType oauthServerType) {
        return builder()
                .nickname(nickname)
                .email(email)
                .profileImageUrl(profileImageUrl)
                .birthday(birthday)
                .shareUrl(shareUrl)
                .mbti(mbti)
                .gender(gender)
                .oauthServerType(oauthServerType)
                .build();
    }

    public void updateUser(String nickname, String profileImageUrl, Mbti mbti) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.mbti = mbti;
    }
}
