package nodawoon.me_to_you.global.error;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidErrorResponse {

    private final int status;
    private final String message;
    private final Map<String, String> errors;
    private final String path;

    public ValidErrorResponse(int status, String message) {
        this(status, message, null, null);
    }

    public ValidErrorResponse(int status, String message, Map<String, String> errors, String path) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.path = path;
    }
}
