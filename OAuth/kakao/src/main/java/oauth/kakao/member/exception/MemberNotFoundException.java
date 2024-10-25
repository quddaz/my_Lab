package oauth.kakao.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oauth.kakao.global.exception.errorCode.ErrorCode;
@Getter
@RequiredArgsConstructor
public class MemberNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;
}
