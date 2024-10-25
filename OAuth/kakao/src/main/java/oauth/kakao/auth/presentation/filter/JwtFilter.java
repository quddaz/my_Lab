package oauth.kakao.auth.presentation.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oauth.kakao.auth.application.jwt.JwtTokenProvider;
import oauth.kakao.auth.util.AuthenticationUtil;
import oauth.kakao.member.domain.Member;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String token = resolveToken(request);
        String requestURI = request.getRequestURI();

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            Member member = jwtTokenProvider.getMember(token);
            AuthenticationUtil.makeAuthentication(member);

            log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", member.getMemberId(), requestURI);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}