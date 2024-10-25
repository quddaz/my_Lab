package oauth.kakao.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import oauth.kakao.global.exception.errorCode.ErrorCode;
import oauth.kakao.global.exception.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Object> handleExceptionInternal(final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(final ErrorCode errorCode) {
        return ErrorResponse.builder()
            .isSuccess(false)
            .code(errorCode.name())
            .message(errorCode.getMessage())
            .results(new ErrorResponse.ValidationErrors(null))
            .build();
    }
}
