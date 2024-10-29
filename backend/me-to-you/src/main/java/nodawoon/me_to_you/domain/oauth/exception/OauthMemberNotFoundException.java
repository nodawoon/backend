package nodawoon.me_to_you.domain.oauth.exception;

import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class OauthMemberNotFoundException extends MainException {

    public static final MainException EXCEPTION = new OauthMemberNotFoundException();

    private OauthMemberNotFoundException() {
        super(ErrorCode.OAUTH_MEMBER_NOT_FOUND);
    }
}
