package nodawoon.me_to_you.domain.file.exception;


import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class BadFileExtensionException extends MainException {

    public static final MainException EXCEPTION = new BadFileExtensionException();
    private BadFileExtensionException() {
        super(ErrorCode.BAD_FILE_EXTENSION);
    }
}