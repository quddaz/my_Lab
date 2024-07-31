package JWT.OAuth20.jwt;

import JWT.OAuth20.oauth.dto.CustomOAuth2User;
import JWT.OAuth20.oauth.dto.MemberDTO;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil; // JWT 유틸리티를 주입받음

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Authorization 쿠키를 찾기 위한 변수
        String authorization = null;

        // 요청의 모든 쿠키를 가져옴
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 쿠키의 이름이 "Authorization"인 경우, 쿠키 값을 가져옴
                if ("Authorization".equals(cookie.getName())) {
                    authorization = cookie.getValue();
                    break; // 원하는 쿠키를 찾았으므로 루프 종료
                }
            }
        }

        // Authorization 쿠키가 없거나 JWT 토큰이 만료된 경우
        if (authorization == null || jwtUtil.isExpired(authorization)) {
            // 필터 체인을 계속 진행하여 다음 필터나 요청 처리기로 전달
            filterChain.doFilter(request, response);
            return;
        }

        // 유효한 JWT 토큰이 존재하는 경우
        String token = authorization;

        // JWT 토큰에서 사용자 정보를 추출하여 MemberDTO를 생성
        MemberDTO memberDTO = MemberDTO.builder()
            .username(jwtUtil.getUsername(token)) // 토큰에서 사용자 이름 추출
            .role(jwtUtil.getRole(token))         // 토큰에서 사용자 역할 추출
            .build();

        log.info("{}", memberDTO.getEmail());
        // CustomOAuth2User 객체를 생성하여 사용자 정보를 설정
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(memberDTO);

        // 사용자 정보를 기반으로 Spring Security의 Authentication 객체를 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(
            customOAuth2User,                  // 사용자 정보
            null,                              // 비밀번호는 null (OAuth2에서는 사용되지 않음)
            customOAuth2User.getAuthorities()  // 사용자 권한
        );

        // SecurityContext에 Authentication을 설정하여 현재 사용자를 인증 상태로 설정
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 필터 체인을 계속 진행하여 다음 필터나 요청 처리기로 전달
        filterChain.doFilter(request, response);
    }
}
