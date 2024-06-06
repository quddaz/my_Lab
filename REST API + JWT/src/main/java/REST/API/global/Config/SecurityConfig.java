package REST.API.global.Config;

import REST.API.global.Config.jwt.JwtFilter;
import REST.API.global.Config.jwt.JwtTokenProvider;
import REST.API.global.Config.jwt.LoginFilter;
import REST.API.global.Config.jwt.Serivce.RefreshService;
import REST.API.global.Utils.CookieStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
  private final AuthenticationConfiguration authenticationConfiguration;
  private final JwtTokenProvider jwtTokenProvider;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final CookieStore cookieStore;
  private final RefreshService refreshService;
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

    return configuration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    //csrf disable
    http
        .csrf((auth) -> auth.disable());

    //From 로그인 방식 disable
    http
        .formLogin((auth) -> auth.disable());

    //http basic 인증 방식 disable
    http
        .httpBasic((auth) -> auth.disable());
    //JWTFilter 등록
    //로그인 필터보다 앞에 두어 자동로그인 구현
    http
        .addFilterBefore(new JwtFilter(jwtTokenProvider), LoginFilter.class);
    http
        .addFilterBefore(new CustomLogoutFilter(jwtTokenProvider, refreshService), LogoutFilter.class);
    //필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
    http
        .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),jwtTokenProvider,refreshService,cookieStore), UsernamePasswordAuthenticationFilter.class);

    //경로별 인가 작업
    http
        .authorizeHttpRequests((auth) -> auth
            .requestMatchers("/login", "/users/join").permitAll()
            .requestMatchers("/reissue").permitAll()
            .anyRequest().authenticated());

    //세션 설정
    //JWT 방식은 항상 상태없음 방식으로 동작하기에 설정해줘야 한다.
    http
        .sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }
}