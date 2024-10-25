package oauth.kakao.auth.util.kakao;

import oauth.kakao.auth.dto.response.KakaoUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoClient", url = "${kakao.feign.base-url}")
public interface KakaoClient {

    @GetMapping(value = "/v2/user/me", consumes = "application/json")
    KakaoUserResponse getUserData(@RequestHeader("Authorization") String accessToken);
}