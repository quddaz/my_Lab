package oauth.plus.auth.application;


import lombok.RequiredArgsConstructor;
import oauth.plus.auth.dto.social.GoogleResponse;
import oauth.plus.auth.dto.social.KakaoResponse;
import oauth.plus.auth.dto.social.NaverResponse;
import oauth.plus.auth.dto.social.OAuth2Response;
import oauth.plus.auth.exception.LoginTypeNotSupportException;
import oauth.plus.auth.exception.errorcode.AuthErrorCode;
import oauth.plus.member.domain.Member;
import oauth.plus.member.domain.type.Role;
import oauth.plus.member.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    @Override
    public Member loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest); // OAuth2 사용자 정보를 가져옴

        // OAuth2Response를 제공업체에 따라 추출
        OAuth2Response oAuth2Response = getOAuth2Response(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        return getOrGenerateMember(oAuth2Response);
    }

    private OAuth2Response getOAuth2Response(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) {
            case "naver" -> new NaverResponse(attributes);
            case "google" -> new GoogleResponse(attributes);
            case "kakao" -> new KakaoResponse(attributes);
            default ->  throw new LoginTypeNotSupportException(AuthErrorCode.LOGIN_TYPE_NOT_SUPPORT);
        };
    }
    private Member getOrGenerateMember(OAuth2Response oAuth2Response){
        return memberRepository.findBySocialId(oAuth2Response.getProviderId())
            .orElseGet(() -> memberRepository.save(Member.builder()
                .name(oAuth2Response.getName())
                .email(oAuth2Response.getEmail())
                .role(Role.GUEST)
                .oAuthType(oAuth2Response.getProvider())
                .socialId(oAuth2Response.getProviderId())
                .profile_image(oAuth2Response.getProfileImage())
                .build())
            );
    }

}
