package quddaz.myblog.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
  @Bean
  public SecurityFilterChain Security(HttpSecurity httpSecurity)throws Exception{
    httpSecurity
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers("/login","/","/loginProc","/join").permitAll() //해당 위치는 접근 가능
                .requestMatchers("/admin").hasRole("Admin") // 어드민 Role을 가지고 있는 사용자만 접근 가능
                .anyRequest().authenticated() // 나머지 모든 페이지는 인증 검사
        );
    httpSecurity
        .formLogin(login ->
            login
                .loginPage("/login")//로그인 페이지 설정
                .loginProcessingUrl("/loginProc")//시큐리티 로그인 처리 로직 url 지정
                .usernameParameter("userName")
                .passwordParameter("password")
                .permitAll()
        );
    httpSecurity
        .csrf((auth)->auth.disable());
    return httpSecurity.build();
  }
}
