package nodawoon.me_to_you.global.oauth.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.kakao")
public record KakaoOauthConfig (
        String redirectUri,
        String clientId,
        String clientSecret,
        String[] scope
){
}
