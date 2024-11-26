package nodawoon.me_to_you.global.oauth.google.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GoogleToken(
        String accessToken,
        int expiresIn,
        String scope,
        String tokenType,
        String idToken
) {
}
