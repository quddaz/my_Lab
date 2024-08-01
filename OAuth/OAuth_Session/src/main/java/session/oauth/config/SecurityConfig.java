package session.oauth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import session.oauth.Service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http //개발환경이기에 csrf off
            .csrf((csrf) -> csrf.disable());
        http
            .formLogin((login) -> login.disable());
        http //httpBasic 방식 인증 off
            .httpBasic((basic) -> basic.disable());
        http
            .oauth2Login((oauth2) -> oauth2
                .loginPage("/login")
                .defaultSuccessUrl("/", true)  // 로그인 성공 후 리디렉션할 URL
                .userInfoEndpoint((userInfoEndpointConfig) ->
                    userInfoEndpointConfig.userService(customOAuth2UserService)));
        http
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers( "/oauth2/**", "/login/**").permitAll()
                .anyRequest().authenticated());

        return http.build();
    }
}