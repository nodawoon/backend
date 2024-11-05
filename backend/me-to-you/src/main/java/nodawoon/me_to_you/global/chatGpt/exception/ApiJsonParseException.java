package nodawoon.me_to_you.global.chatGpt.exception;


import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class ApiJsonParseException extends MainException {

    public static final MainException EXCEPTION = new ApiJsonParseException();

    private ApiJsonParseException() {
        super(ErrorCode.API_JSON_PARSE_ERROR);
    }
}