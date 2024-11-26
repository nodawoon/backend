package nodawoon.me_to_you.global.oauth.naver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodawoon.me_to_you.domain.oauth.client.OauthMemberClient;
import nodawoon.me_to_you.domain.oauth.domain.OauthMember;
import nodawoon.me_to_you.domain.oauth.domain.OauthServerType;
import nodawoon.me_to_you.global.oauth.naver.client.NaverApiClient;
import nodawoon.me_to_you.global.oauth.naver.dto.NaverMemberResponse;
import nodawoon.me_to_you.global.oauth.naver.dto.NaverToken;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class NaverMemberClient implements OauthMemberClient {
    private final NaverApiClient naverApiClient;
    private final NaverOauthConfig naverOauthConfig;

    @Override
    public OauthServerType supportServer() {
        return OauthServerType.NAVER;
    }

    @Override
    public OauthMember fetch(String authCode) {
        NaverToken tokenInfo = naverApiClient.fetchToken(tokenRequestParams(authCode));
        NaverMemberResponse naverMemberResponse = naverApiClient.fetchMember("Bearer " + tokenInfo.accessToken());
        return naverMemberResponse.toDomain();
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverOauthConfig.clientId());
        params.add("client_secret", naverOauthConfig.clientSecret());
        params.add("code", authCode);
        params.add("state", naverOauthConfig.state());
        return params;
    }
}
