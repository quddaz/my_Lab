package quddaz.jwtTest.Config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import quddaz.jwtTest.auth.CustomUserDetails;
import quddaz.jwtTest.Entity.UserEntity;

import java.io.IOException;

/**
 * jwt를 검증하기 위한 커스텀 필터
 * 해당 필터를 통해 요청 헤더 키에 jwt가 존재하는 경우 jwt를 검증하고
 * 강제로 세션을 생성한다.
 */
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

  private final JWTUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    //request에서 Authorization 헤더를 찾음
    String authorization= request.getHeader("Authorization");

    //Authorization 헤더 검증
    if (authorization == null || !authorization.startsWith("Bearer ")) {

      System.out.println("token null");
      //필터 다음으로 넘겨줌(JWT 검증 -> 로그인)
      filterChain.doFilter(request, response);

      //조건이 해당되면 메소드 종료 (필수)
      return;
    }

    System.out.println("authorization now");
    //Bearer 부분 제거 후 순수 토큰만 획득
    String token = authorization.split(" ")[1];

    //토큰 소멸 시간 검증
    if (jwtUtil.isExpired(token)) {

      System.out.println("token expired");
      filterChain.doFilter(request, response);

      //조건이 해당되면 메소드 종료 (필수)
      return;
    }

    //토큰에서 username과 role 획득
    String username = jwtUtil.getUsername(token);
    String role = jwtUtil.getRole(token);

    //userEntity를 생성하여 값 set
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(username);
    userEntity.setPassword("temppassword");
    userEntity.setRole(role);

    //UserDetails에 회원 정보 객체 담기
    CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

    //스프링 시큐리티 인증 토큰 생성
    Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
    //세션에 사용자 등록
    SecurityContextHolder.getContext().setAuthentication(authToken);

    filterChain.doFilter(request, response);
  }
}
