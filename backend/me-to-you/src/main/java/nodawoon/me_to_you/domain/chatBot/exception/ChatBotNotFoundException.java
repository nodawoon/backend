package nodawoon.me_to_you.domain.chatBot.exception;

import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class ChatBotNotFoundException extends MainException {
    public static final MainException EXCEPTION = new ChatBotNotFoundException();

    private ChatBotNotFoundException() {
        super(ErrorCode.CHATBOT_NOT_FOUND);
    }
}
