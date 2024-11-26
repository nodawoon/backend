package nodawoon.me_to_you.domain.oauth.client;


import nodawoon.me_to_you.domain.oauth.domain.OauthMember;
import nodawoon.me_to_you.domain.oauth.domain.OauthServerType;

public interface OauthMemberClient {

    OauthServerType supportServer();

    OauthMember fetch(String code);
}