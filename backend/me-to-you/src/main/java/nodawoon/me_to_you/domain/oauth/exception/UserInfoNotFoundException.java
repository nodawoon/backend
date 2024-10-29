package nodawoon.me_to_you.domain.oauth.exception;


import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class UserInfoNotFoundException extends MainException {

    public static final MainException EXCEPTION = new UserInfoNotFoundException();

    private UserInfoNotFoundException() {
        super(ErrorCode.USER_INFO_NOT_FOUND);
    }
}
