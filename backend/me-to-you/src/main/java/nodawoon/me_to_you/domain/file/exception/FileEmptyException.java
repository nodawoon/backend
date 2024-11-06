package nodawoon.me_to_you.domain.file.exception;


import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class FileEmptyException extends MainException {

    public static final MainException EXCEPTION = new FileEmptyException();

    private FileEmptyException() {
        super(ErrorCode.FILE_EMPTY);
    }
}