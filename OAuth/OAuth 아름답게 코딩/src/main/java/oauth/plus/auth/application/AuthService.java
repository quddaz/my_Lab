package oauth.plus.auth.application;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import oauth.plus.auth.application.jwt.JwtTokenProvider;
import oauth.plus.auth.exception.TokenNotValidException;
import oauth.plus.auth.exception.errorcode.AuthErrorCode;
import oauth.plus.auth.utils.CookieStore;
import oauth.plus.member.domain.Member;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieStore cookieStore;
    public void reIssueToken(String refreshToken, HttpServletResponse response) {

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new TokenNotValidException(AuthErrorCode.TOKEN_NOT_VALID);
        }

        Member member = jwtTokenProvider.getMember(refreshToken);

        String accessToken = jwtTokenProvider.createAccessToken(member);

        response.addCookie(cookieStore.createCookie(jwtTokenProvider.createRefreshToken(member)));

        response.setHeader("Authorization", "Bearer " + accessToken);
    }
}
