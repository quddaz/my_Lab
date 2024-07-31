package quddaz.myblog.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import quddaz.myblog.component.CustomAuthFailureHandler;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
  private final CustomAuthFailureHandler customAuthFailureHandler;
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  @Bean
  public SecurityFilterChain Security(HttpSecurity httpSecurity)throws Exception{
    httpSecurity
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers("/login","/","/loginProc","/join","/css/**").permitAll() //해당 위치는 접근 가능
                //.requestMatchers("/admin").hasRole("Admin") // 어드민 Role을 가지고 있는 사용자만 접근 가능
                .anyRequest().authenticated() // 나머지 모든 페이지는 인증 검사
        );
    httpSecurity
        .formLogin(login ->
            login
                .loginPage("/login")//로그인 페이지 설정
                .loginProcessingUrl("/loginProc")//시큐리티 로그인 처리 로직 url 지정
                .defaultSuccessUrl("/board",true)
                .usernameParameter("userName")
                .failureHandler(customAuthFailureHandler)
                .passwordParameter("password")
                .permitAll()
        );
    httpSecurity
        .csrf((auth)->auth.disable());
    httpSecurity
        .logout(logout -> logout
            .logoutUrl("/logout")  // 로그아웃 URL 지정
            .logoutSuccessUrl("/loginPage")  // 로그아웃 성공 시 이동할 URL 지정
            .invalidateHttpSession(true)  // 세션 무효화
            .deleteCookies("JSESSIONID")// 쿠키 삭제
            .permitAll());
    return httpSecurity.build();
  }
}
