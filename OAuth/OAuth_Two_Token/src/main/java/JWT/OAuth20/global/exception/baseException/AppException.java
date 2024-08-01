package JWT.OAuth20.global.exception.baseException;

import JWT.OAuth20.global.exception.errorCode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException {
    private ErrorCode errorCode;
}