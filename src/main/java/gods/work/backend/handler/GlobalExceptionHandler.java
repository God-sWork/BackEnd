package gods.work.backend.handler;

import ch.qos.logback.core.spi.ErrorCodes;
import gods.work.backend.config.error.ErrorCode;
import gods.work.backend.config.error.exception.BusinessBaseException;
import gods.work.backend.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException", e.getMessage());
        return createErrorResponseEntity(ErrorCode.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(BusinessBaseException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessBaseException(BusinessBaseException e) {
        log.error("BusinessBaseException", e.getMessage());
        return createErrorResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        e.printStackTrace();
        log.error("Exception", e);
        return createErrorResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> createErrorResponseEntity(ErrorCode errorCode) {
        return new ResponseEntity<>(
                ErrorResponse.of(errorCode),
                errorCode.getStatus());
    }
}
