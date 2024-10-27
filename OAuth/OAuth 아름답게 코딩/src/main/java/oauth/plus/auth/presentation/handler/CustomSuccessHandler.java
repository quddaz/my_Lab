package oauth.plus.auth.presentation.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import oauth.plus.auth.application.jwt.JwtTokenProvider;
import oauth.plus.auth.utils.CookieStore;
import oauth.plus.member.domain.Member;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final CookieStore cookieStore;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Member member = (Member) authentication.getPrincipal();

        String accessToken = jwtTokenProvider.createAccessToken(member);
        String refreshToken = jwtTokenProvider.createRefreshToken(member);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.addCookie(cookieStore.createCookie(refreshToken));

        //리다이렉트
        response.sendRedirect("http://localhost:3000/");
    }
}