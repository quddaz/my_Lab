package JWT.OAuth20.global.jwt;

import JWT.OAuth20.global.auth.CustomOAuth2User;
import JWT.OAuth20.global.auth.MemberDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("access");

        // AccessToken이 없거나, 만료된 경우
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (jwtUtil.isExpired(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 카테고리가 Access인지 확인
        if (!"access".equals(jwtUtil.getCategory(accessToken))) {
            log.warn("Invalid token category");
            filterChain.doFilter(request, response);
            return;
        }

        // 유효한 JWT 토큰이 존재하는 경우
        String token = accessToken;

        // JWT 토큰에서 사용자 정보를 추출하여 MemberDTO를 생성
        MemberDTO memberDTO = MemberDTO.builder()
            .username(jwtUtil.getUsername(token))
            .role(jwtUtil.getRole(token))
            .build();

        // CustomOAuth2User 객체를 생성하여 사용자 정보를 설정
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(memberDTO);

        // 사용자 정보를 기반으로 Spring Security의 Authentication 객체를 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(
            customOAuth2User,
            null,
            customOAuth2User.getAuthorities()
        );

        // SecurityContext에 Authentication을 설정하여 현재 사용자를 인증 상태로 설정
        SecurityContextHolder.getContext().setAuthentication(authToken);

        log.info("User authenticated: {}", memberDTO.getUsername());

        // 필터 체인을 계속 진행하여 다음 필터나 요청 처리기로 전달
        filterChain.doFilter(request, response);
    }
}