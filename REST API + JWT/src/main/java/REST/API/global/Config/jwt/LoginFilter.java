package REST.API.global.Config.jwt;

import REST.API.User.domain.dto.UserLoginRequest;
import REST.API.global.Utils.CookieStore;
import REST.API.global.Config.jwt.Serivce.RefreshService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshService refreshService;
    private final CookieStore cookieStore;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 요청 본문에서 JSON 데이터를 읽어옴
            UserLoginRequest userLoginRequest = objectMapper.readValue(request.getReader(), UserLoginRequest.class);

            // 유효한 JSON 데이터가 아닌 경우 null 반환
            if (!isValidJsonAuthenticationRequest(userLoginRequest)) {
                return null;
            }

            // UsernamePasswordAuthenticationToken 생성
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userLoginRequest.getUserName(),
                userLoginRequest.getPassword()
            );

            // AuthenticationManager에게 인증 요청 전달
            return authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValidJsonAuthenticationRequest(UserLoginRequest userLoginRequest) {
        return userLoginRequest != null && StringUtils.hasText(userLoginRequest.getUserName()) && StringUtils.hasText(userLoginRequest.getPassword());
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        String userName = authentication.getName();
        //토큰 생성
        String AccessToken = jwtTokenProvider.createAccessToken("access",userName);
        String RefreshToken = jwtTokenProvider.createRefreshToken("refresh",userName);
        //Refresh 토큰 저장
        refreshService.addRefresh(userName, RefreshToken);

        //응답 설정
        response.setHeader("access", AccessToken);
        response.addCookie(cookieStore.createCookie("refresh", RefreshToken));
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed){
        response.setStatus(401);
    }
}