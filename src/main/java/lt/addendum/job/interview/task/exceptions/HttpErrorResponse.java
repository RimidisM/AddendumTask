package lt.addendum.job.interview.task.exceptions;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class HttpErrorResponse {

    private final String timestamp = ZonedDateTime.now().toString();
    private String type;
    private String code;
    private String error;
    private String detail;

    public HttpErrorResponse(String type, String code, String error, String detail) {
        this.type = type;
        this.code = code;
        this.error = error;
        this.detail = detail;
    }
}
