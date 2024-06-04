package REST.API.global.Controller;

import REST.API.global.Config.jwt.JwtTokenProvider;
import REST.API.global.Exception.BaseException.AppException;
import REST.API.global.Exception.errorCode.AuthErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReissueController {
    private final JwtTokenProvider jwtTokenProvider;

    public ReissueController(JwtTokenProvider jwtTokenProvider) {

        this.jwtTokenProvider = jwtTokenProvider;
    }
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        //쿠키에서 Refresh 토큰 얻기
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {

            throw new AppException(AuthErrorCode.REFRESH_TOKEN_NULL);
        }

        //만료 시간 체크
        try {
            jwtTokenProvider.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            throw new AppException(AuthErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtTokenProvider.getCategory(refresh);

        if (!category.equals("refresh")) {

            //response status code
            throw new AppException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        String userName = jwtTokenProvider.getUsername(refresh);

        //make new JWT
        String newAccess = jwtTokenProvider.createAccessToken("access", userName);

        //response
        response.setHeader("access", newAccess);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
