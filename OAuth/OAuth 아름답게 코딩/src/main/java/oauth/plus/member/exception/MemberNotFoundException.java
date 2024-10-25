package oauth.plus.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oauth.plus.global.exception.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public class MemberNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;
}
