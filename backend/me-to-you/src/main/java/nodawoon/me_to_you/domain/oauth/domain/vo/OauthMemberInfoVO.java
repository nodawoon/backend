package nodawoon.me_to_you.domain.oauth.domain.vo;


import nodawoon.me_to_you.domain.oauth.domain.OauthServerType;

public record OauthMemberInfoVO(
        String email,
        String name,
        OauthServerType oauthServerType
) {
}
