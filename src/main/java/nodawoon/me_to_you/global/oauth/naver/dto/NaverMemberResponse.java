package nodawoon.me_to_you.global.oauth.naver.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import nodawoon.me_to_you.domain.oauth.domain.OauthMember;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static nodawoon.me_to_you.domain.oauth.domain.OauthServerType.NAVER;

@JsonNaming(SnakeCaseStrategy.class)
public record NaverMemberResponse (
        String resultcode,
        String message,
        Response response
){
    public OauthMember toDomain() {
        return OauthMember.builder()
                .name(response.name)
                .email(response.email)
                .oauthServerType(NAVER)
                .build();


    }
    @JsonNaming(SnakeCaseStrategy.class)
    public record Response(
            String id,
            String nickname,
            String name,
            String email,
            String gender,
            String age,
            String birthday,
            String profileImage,
            String birthyear,
            String mobile


    ){

    }
}
