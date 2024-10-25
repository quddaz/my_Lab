package oauth.kakao.global.config;


import lombok.RequiredArgsConstructor;
import oauth.kakao.auth.presentation.filter.JwtAccessDeniedHandler;
import oauth.kakao.auth.presentation.filter.JwtAuthenticationEntryPoint;
import oauth.kakao.auth.presentation.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtFilter jwtFilter;
    private final WebCorsConfig webCorsConfig;
    private static final String MEMBER = "MEMBER";
    private static final String ADMIN = "ADMIN";

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
        return web -> web.ignoring()
            .requestMatchers(
                "/error",
                "/webjars/**",
                "/auth/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .formLogin(AbstractHttpConfigurer::disable) // 기본 login form 비활성화
            .httpBasic(AbstractHttpConfigurer::disable) // HTTP 기본 인증을 비활성화
            .cors(cors -> cors.configurationSource(webCorsConfig.corsConfigurationSource())) // CORS 활성화 - corsConfigurationSource 이름의 빈 사용
            .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 기능 비활성화
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth // 요청에 대한 인증 설정
                .requestMatchers("/logout", "/auth/refresh-token").hasRole(MEMBER)
                .requestMatchers("/programs/edit/**").hasRole(ADMIN) //수정
                .anyRequest().authenticated())  //이외의 요청은 전부 인증 필요
            .exceptionHandling(exceptionHandling -> {
                exceptionHandling
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint) //인증되지 않은 사용자가 보호된 리소스에 액세스 할 때 호출
                    .accessDeniedHandler(jwtAccessDeniedHandler); //권한이 없는 사용자가 보호된 리소스에 액세스 할 때 호출
            })
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}