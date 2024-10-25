package oauth.kakao.auth.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oauth.kakao.global.exception.errorCode.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    LOGIN_TYPE_NOT_SUPPORT(HttpStatus.NOT_ACCEPTABLE, "login type not support."),
    TOKEN_NOT_VALID(HttpStatus.NOT_ACCEPTABLE, "refresh token is not valid."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}