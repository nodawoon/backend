package nodawoon.me_to_you.domain.chatBot.exception;

import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class QuestionUserNotHostChatBotException extends MainException {
    public static final MainException EXCEPTION = new QuestionUserNotHostChatBotException();

    private QuestionUserNotHostChatBotException() {
        super(ErrorCode.QUESTION_USER_NOT_CHATBOT_HOST);
    }
}
