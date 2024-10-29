package nodawoon.me_to_you.global.oauth.google.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import nodawoon.me_to_you.domain.oauth.domain.OauthMember;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static nodawoon.me_to_you.domain.oauth.domain.OauthServerType.GOOGLE;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GoogleMemberResponse(
        String id,
        String email,
        Boolean verifiedEmail,
        String name,
        String givenName,
        String familyName,
        String picture,
        String locale
) {
    public OauthMember toDomain() {
        return OauthMember.builder()
                .name(name)
                .email(email)
                .oauthServerType(GOOGLE)
                .build();
    }
}
