package nodawoon.me_to_you.global.oauth.naver;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.naver")
public record NaverOauthConfig(
        String redirectUri,
        String clientId,
        String clientSecret,
        String[] scope,
        String state
) {
}
