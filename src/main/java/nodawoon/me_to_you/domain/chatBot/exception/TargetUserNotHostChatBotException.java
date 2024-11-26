package nodawoon.me_to_you.domain.chatBot.exception;

import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class TargetUserNotHostChatBotException extends MainException {
    public static final MainException EXCEPTION = new TargetUserNotHostChatBotException();

    private TargetUserNotHostChatBotException() {
        super(ErrorCode.TARGET_USER_NOT_CHATBOT_HOST);
    }
}
