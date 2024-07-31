package REST.API.global.Utils;

import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class CookieStore {

    //쿠키 생성 메소드
    public Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60); // 쿠키의 생명 주기
        //cookie.setSecure(true); //https 통신 시 진행
        //cookie.setPath("/"); //쿠키의 적용 범위 설정 가능
        cookie.setHttpOnly(true); //XSS 방어를 위한 설정

        return cookie;
    }
}
