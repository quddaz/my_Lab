package oauth.kakao.auth.util.kakao;

import lombok.RequiredArgsConstructor;
import oauth.kakao.auth.dto.response.KakaoUserResponse;
import oauth.kakao.auth.util.SocialLoginProvider;
import oauth.kakao.member.domain.Member;
import oauth.kakao.member.domain.type.Gender;
import oauth.kakao.member.domain.type.OAuthType;
import oauth.kakao.member.domain.type.Role;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoLoginProvider implements SocialLoginProvider {

    private final KakaoClient kakaoClient;

    @Override
    public Member getUserData(String accessToken) {
        KakaoUserResponse kakaoUserResponse = kakaoClient.getUserData("Bearer " + accessToken);

        return Member.builder()
            .email(kakaoUserResponse.kakaoAccount().email())
            .role(Role.GUEST)
            .socialId(kakaoUserResponse.id().toString())    //social id
            .oAuthType(OAuthType.KAKAO)
            .build();
    }
}