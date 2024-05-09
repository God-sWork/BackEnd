package gods.work.backend.dto;

import gods.work.backend.config.error.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String code;
    private String message;

    private ErrorResponse(final ErrorCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public ErrorResponse(final ErrorCode code, final String message) {
        this.message = message;
        this.code = code.getCode();
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ErrorCode code, final String message) {
        return new ErrorResponse(code, message);
    }
}
