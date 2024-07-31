package session.oauth.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import session.oauth.entity.CustomOAuth2User;
import session.oauth.DTO.GoogleResponse;
import session.oauth.DTO.NaverResponse;
import session.oauth.DTO.OAuth2Response;
import session.oauth.entity.Member;
import session.oauth.repository.MemberRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest); // 유저 정보를 가지고 옴
        System.out.println(oAuth2User.getAttributes()); // 간단한 확인

        OAuth2Response oAuth2Response = getOAuth2Response(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        if (oAuth2Response == null) {
            return null;
        }

        String username = oAuth2Response.getName();
        Member member = updateOrCreateMember(username, oAuth2Response);

        return new CustomOAuth2User(oAuth2Response, member.getRole());
    }

    private OAuth2Response getOAuth2Response(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) {
            case "naver" -> new NaverResponse(attributes);
            case "google" -> new GoogleResponse(attributes);
            default -> null;
        };
    }


    private Member updateOrCreateMember(String username, OAuth2Response oAuth2Response) {
        Member member = memberRepository.findByUsername(username);

        if (member == null) {
            member = Member.builder()
                .username(username)
                .email(oAuth2Response.getEmail())
                .role("ROLE_USER")
                .build();
        } else {
            member.setUsername(username);
            member.setEmail(oAuth2Response.getEmail());
        }

        return memberRepository.save(member);
    }
}
