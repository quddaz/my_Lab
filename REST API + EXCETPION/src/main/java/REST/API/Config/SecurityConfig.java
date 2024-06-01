package REST.API.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
    //CSRF 보안 요소 disable
    httpSecurity
        .csrf((auth) -> auth.disable());
    //http basic 인증 방식 disable
    httpSecurity
        .httpBasic((auth) -> auth.disable());
    httpSecurity
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers("/api/v1/users/join","/api/v1/users/login").permitAll() //해당 위치는 접근 가능
                .anyRequest().authenticated() // 나머지 모든 페이지는 인증 검사
        );
    httpSecurity
        .sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //JWT를 사용하기 위한 세션 무상태 설정
    return httpSecurity.build();


  }
}
