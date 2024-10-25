package oauth.kakao.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oauth.kakao.global.exception.errorCode.ErrorCode;

@Getter
@RequiredArgsConstructor
public class LoginTypeNotSupportException extends RuntimeException {
    private final ErrorCode errorCode;
}