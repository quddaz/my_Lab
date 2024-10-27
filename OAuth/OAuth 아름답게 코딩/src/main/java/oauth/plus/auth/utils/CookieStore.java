package oauth.plus.auth.utils;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import oauth.plus.auth.application.jwt.JwtProperties;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieStore {
    private final JwtProperties jwtProperties;
    public Cookie createCookie(String refreshToken) {
        Cookie cookie = new Cookie("REFRESH_TOKEN", refreshToken);
        cookie.setSecure(true);
        cookie.setMaxAge((int) (jwtProperties.getRefreshTokenExpiration()/1000));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }
}