package oauth.kakao.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = {"oauth.kakao"})
@Configuration
public class OpenFeignConfig {

}