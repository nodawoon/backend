package nodawoon.me_to_you.global.oauth.google.client;

import nodawoon.me_to_you.global.oauth.google.dto.GoogleMemberResponse;
import nodawoon.me_to_you.global.oauth.google.dto.GoogleToken;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

public interface GoogleApiClient {

    @PostExchange(url = "https://oauth2.googleapis.com/token")
    GoogleToken fetchToken(@RequestParam(name = "params") MultiValueMap<String, String> params);

    @GetExchange(url = "https://www.googleapis.com/oauth2/v2/userinfo")
    GoogleMemberResponse fetchMember(@RequestHeader(name = AUTHORIZATION) String bearerToken);
}
