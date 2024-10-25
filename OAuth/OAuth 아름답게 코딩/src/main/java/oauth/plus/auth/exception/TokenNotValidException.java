package oauth.plus.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oauth.plus.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public class TokenNotValidException extends RuntimeException {
    private final ErrorCode errorCode;
}