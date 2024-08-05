package REST.API.global.Config.jwt;

import REST.API.global.Exception.BaseException.AppException;
import REST.API.global.Exception.errorCode.AuthErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * jwt를 검증하기 위한 커스텀 필터
 * 해당 필터를 통해 요청 헤더 키에 jwt가 존재하는 경우 jwt를 검증하고
 * 강제로 세션을 생성한다.
 */
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 헤더에서 Access Token을 꺼냄
        String accessToken = request.getHeader("access");

        // 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtTokenProvider.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            throw new AppException(AuthErrorCode.ACCESS_TOKEN_EXPIRED);
        }

        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtTokenProvider.getCategory(accessToken);

        if (!category.equals("access")) {
            throw new AppException(AuthErrorCode.INVALID_ACCESS_TOKEN);
        }

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = jwtTokenProvider.getAuthentication(accessToken);

        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

}