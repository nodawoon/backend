package nodawoon.me_to_you.global.oauth.naver.client;

import nodawoon.me_to_you.global.oauth.naver.dto.NaverMemberResponse;
import nodawoon.me_to_you.global.oauth.naver.dto.NaverToken;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

public interface NaverApiClient {

    @PostExchange(url = "https://nid.naver.com/oauth2.0/token")
    NaverToken fetchToken(@RequestParam(name = "params") MultiValueMap<String, String> params);

    @GetExchange("https://openapi.naver.com/v1/nid/me")
    NaverMemberResponse fetchMember(@RequestHeader(name = AUTHORIZATION) String bearerToken);
}
