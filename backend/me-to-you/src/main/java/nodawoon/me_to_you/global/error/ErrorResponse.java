package nodawoon.me_to_you.global.error;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final boolean success = false;
    private final int status;
    private final String reason;
    private final LocalDateTime timeStamp;


    public ErrorResponse(int status, String reason) {
        this.status = status;
        this.reason = reason;
        this.timeStamp = LocalDateTime.now();
    }
}
