package REST.API.global.Controller;

import REST.API.global.Utils.CookieStore;
import REST.API.global.Config.jwt.JwtTokenProvider;
import REST.API.global.Exception.BaseException.AppException;
import REST.API.global.Exception.errorCode.AuthErrorCode;
import REST.API.global.Config.jwt.Serivce.RefreshService;
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
@RequiredArgsConstructor
public class ReissueController {
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieStore cookieStore;
    private final RefreshService refreshService;
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
        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshService.existsByRefresh(refresh);
        if (!isExist) {

            throw new AppException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        String userName = jwtTokenProvider.getUsername(refresh);

        //make new JWT
        String newAccess = jwtTokenProvider.createAccessToken("access", userName);
        String newRefresh = jwtTokenProvider.createRefreshToken("refresh", userName);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshService.deleteByRefresh(refresh);
        refreshService.addRefresh(userName, newRefresh);

        //response
        response.setHeader("access", newAccess);
        response.addCookie(cookieStore.createCookie("refresh", newRefresh));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
