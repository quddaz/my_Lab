package REST.API.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

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
    return httpSecurity.build();


  }
}
