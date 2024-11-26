package nodawoon.me_to_you.domain.file.exception;


import nodawoon.me_to_you.global.error.exception.ErrorCode;
import nodawoon.me_to_you.global.error.exception.MainException;

public class ImageProcessingException extends MainException {

    public static final MainException EXCEPTION = new ImageProcessingException();

    private ImageProcessingException() {
        super(ErrorCode.IMAGE_PROCESSING);
    }
}