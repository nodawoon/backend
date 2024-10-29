package nodawoon.me_to_you.domain.oauth.authcode;


import nodawoon.me_to_you.domain.oauth.domain.OauthServerType;

public interface AuthCodeRequestUrlProvider {

    OauthServerType supportServer();

    String provide();
}
