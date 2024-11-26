package nodawoon.me_to_you.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MainException extends RuntimeException{

    private ErrorCode errorCode;
}
