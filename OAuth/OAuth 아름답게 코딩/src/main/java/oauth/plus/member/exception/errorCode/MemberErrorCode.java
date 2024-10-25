package oauth.plus.member.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oauth.plus.global.exception.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member not found"),
    Member_BIRTH_NOT_FOUND(HttpStatus.NOT_FOUND, "Member birth not found");

    private final HttpStatus httpStatus;
    private final String message;
}