package REST.API.global.Config;

import REST.API.global.Config.jwt.JwtTokenProvider;
import REST.API.global.Config.jwt.Repository.RefreshRepository;
import REST.API.global.Config.jwt.Serivce.RefreshService;
import REST.API.global.Exception.BaseException.AppException;
import REST.API.global.Exception.errorCode.AuthErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class CustomLogoutFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshService refreshService;
    public CustomLogoutFilter(JwtTokenProvider jwtTokenProvider, RefreshService refreshService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshService = refreshService;
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //logout 요청인지 확인
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/logout$")) {

            filterChain.doFilter(request, response);
            return;
        }
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {

            filterChain.doFilter(request, response);
            return;
        }

        //Refresh 토큰 받기
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        //Refresh 토큰 null 체크
        if (refresh == null) {
            throw new AppException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        //유효 기간 확인
        try {
            jwtTokenProvider.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            throw new AppException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtTokenProvider.getCategory(refresh);
        if (!category.equals("refresh")) {

            throw new AppException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshService.existsByRefresh(refresh);
        if (!isExist) {

            throw new AppException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        //로그아웃 진행
        //Refresh 토큰 DB에서 제거
        refreshService.deleteByRefresh(refresh);

        //Refresh 토큰 Cookie 값 0
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);

    }
}
