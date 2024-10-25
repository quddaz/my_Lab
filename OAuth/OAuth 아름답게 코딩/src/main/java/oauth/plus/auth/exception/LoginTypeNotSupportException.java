package oauth.plus.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oauth.plus.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public class LoginTypeNotSupportException extends RuntimeException {
    private final ErrorCode errorCode;
}