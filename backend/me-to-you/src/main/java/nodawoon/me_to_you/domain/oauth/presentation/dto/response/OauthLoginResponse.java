package nodawoon.me_to_you.domain.oauth.presentation.dto.response;


import nodawoon.me_to_you.domain.oauth.domain.OauthMember;
import nodawoon.me_to_you.domain.oauth.domain.OauthServerType;

public record OauthLoginResponse(
        String email,
        String name,
        OauthServerType oauthServerType,
        Boolean isFirst
) {
    public OauthLoginResponse (OauthMember oauthMember, Boolean isFirst) {
        this(oauthMember.getEmail(), oauthMember.getName(), oauthMember.getOauthServerType(), isFirst);
    }
}
