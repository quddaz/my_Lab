package oauth.plus.global.config;

import lombok.RequiredArgsConstructor;
import oauth.plus.auth.application.CustomOAuth2UserService;
import oauth.plus.auth.presentation.filter.CustomSuccessHandler;
import oauth.plus.auth.presentation.filter.JwtAccessDeniedHandler;
import oauth.plus.auth.presentation.filter.JwtAuthenticationEntryPoint;
import oauth.plus.auth.presentation.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableAspectJAutoProxy
public class SecurityConfig {

    private final CustomSuccessHandler customSuccessHandler; // 인증 성공 시 처리할 커스텀 핸들러
    private final CustomOAuth2UserService customOAuthUserService; // OAuth2 사용자 정보 서비스
    private final JwtFilter jwtFilter; // jwt 인증용 필터
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final WebCorsConfig webCorsConfig; // Cors 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(webCorsConfig.corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)  // CSRF 보호 비활성화
            .formLogin(AbstractHttpConfigurer::disable)  // Form 기반 로그인 비활성화
            .httpBasic(AbstractHttpConfigurer::disable)  // HTTP Basic 인증 비활성화
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // JWT 필터를 UsernamePasswordAuthenticationFilter 전에 추가
            .exceptionHandling(exceptionHandling -> {
                exceptionHandling
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint) //인증되지 않은 사용자가 보호된 리소스에 액세스 할 때 호출
                    .accessDeniedHandler(jwtAccessDeniedHandler); //권한이 없는 사용자가 보호된 리소스에 액세스 할 때 호출
            })
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                    .userService(customOAuthUserService))
                .successHandler(customSuccessHandler)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login/oauth2/code/**", "/refresh", "/api/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}