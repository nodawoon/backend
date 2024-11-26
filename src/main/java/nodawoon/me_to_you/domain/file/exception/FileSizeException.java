package nodawoon.me_to_you.domain.file.exception;


import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class FileSizeException extends MainException {

    public static final MainException EXCEPTION = new FileSizeException();

    private FileSizeException() {
        super(ErrorCode.FILE_SIZE);
    }
}