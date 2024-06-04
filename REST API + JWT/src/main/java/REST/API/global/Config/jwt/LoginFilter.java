package REST.API.global.Config.jwt;

import REST.API.User.domain.dto.UserLoginRequest;
import REST.API.global.auth.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
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
        //로그인 성공 시 유저 정보 조회
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String userName = customUserDetails.getUsername();

        //토큰 생성
        String AccessToken = jwtTokenProvider.createAccessToken("access",userName);
        String RefreshToken = jwtTokenProvider.createRefreshToken("refresh",userName);
        //응답 설정
        response.setHeader("access", AccessToken);
        response.addCookie(createCookie("refresh", RefreshToken));
        response.setStatus(HttpStatus.OK.value());
    }
    //쿠키 생성 메소드
    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60); // 쿠키의 생명 주기
        //cookie.setSecure(true); //https 통신 시 진행
        //cookie.setPath("/"); //쿠키의 적용 범위 설정 가능
        cookie.setHttpOnly(true); //XSS 방어를 위한 설정

        return cookie;
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed){
        response.setStatus(401);
    }
}