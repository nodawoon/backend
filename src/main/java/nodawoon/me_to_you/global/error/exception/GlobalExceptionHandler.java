package nodawoon.me_to_you.global.error.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodawoon.me_to_you.global.error.ErrorResponse;
import nodawoon.me_to_you.global.error.ValidErrorResponse;
import nodawoon.me_to_you.global.slack.SlackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final SlackService slackService;

    @ExceptionHandler(MainException.class)
    public ResponseEntity<ErrorResponse> mainExceptionHandler(
            MainException e, HttpServletRequest request) throws IOException {

        ErrorCode code = e.getErrorCode();

        ErrorResponse errorResponse =
                new ErrorResponse(
                        code.getStatus(),
                        code.getReason());

        sendSlackMessage(e, code);

        return ResponseEntity.status(HttpStatus.valueOf(code.getStatus())).body(errorResponse);
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<ErrorResponse> bibliophileHandleException(Exception e, HttpServletRequest request)
            throws IOException {

        log.error("INTERNAL_SERVER_ERROR", e);
        ErrorCode internalServerError = ErrorCode.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse =
                new ErrorResponse(
                        internalServerError.getStatus(),
                        internalServerError.getReason());

        sendSlackMessage(e, internalServerError);

        return ResponseEntity.status(HttpStatus.valueOf(internalServerError.getStatus()))
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        log.error("Validation error occurred: {}", errors, ex);

        ValidErrorResponse errorResponse = new ValidErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                errors,
                request.getRequestURI()
        );

        sendSlackMessage(ex, ErrorCode.BAD_REQUEST);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException e) {
        log.error("INTERNAL_SERVER_ERROR", e);
        ErrorCode internalServerError = ErrorCode.MISSING_PARAMS;
        ErrorResponse errorResponse =
                new ErrorResponse(
                        internalServerError.getStatus(),
                        internalServerError.getReason());

        sendSlackMessage(e, ErrorCode.MISSING_PARAMS);

        return ResponseEntity.status(HttpStatus.valueOf(internalServerError.getStatus()))
                .body(errorResponse);
    }

    private void sendSlackMessage(Exception e, ErrorCode errorCode) {
        HashMap<String, String> message = new HashMap<>();
        String errorMessage = e.getMessage() == null ? errorCode.getReason() : e.getMessage();
        message.put("예외 로그", errorMessage);
        log.info("예외 로그 : " + errorMessage);
        slackService.sendMessage(errorCode.getReason(), message);
    }
}
