package JWT.OAuth20.global.oauth.service;

import JWT.OAuth20.global.auth.CustomOAuth2User;
import JWT.OAuth20.global.auth.MemberDTO;
import JWT.OAuth20.entity.Member;
import JWT.OAuth20.global.oauth.dto.GoogleResponse;
import JWT.OAuth20.global.oauth.dto.NaverResponse;
import JWT.OAuth20.global.oauth.dto.OAuth2Response;
import JWT.OAuth20.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest); // OAuth2 사용자 정보를 가져옴
        System.out.println(oAuth2User.getAttributes()); // 디버깅을 위한 출력

        // OAuth2Response를 제공업체에 따라 추출
        OAuth2Response oAuth2Response = getOAuth2Response(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        if (oAuth2Response == null) {
            throw new OAuth2AuthenticationException("지원하지 않는 OAuth2 제공업체입니다.");
        }

        // 사용자 식별자 생성
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        // 사용자 정보를 업데이트하거나 새로 생성
        Member member = updateOrCreateMember(username, oAuth2Response);

        // CustomOAuth2User를 생성하여 반환
        MemberDTO memberDTO = MemberDTO.builder()
            .role(member.getRole())
            .username(username)
            .email(member.getEmail())
            .name(member.getName()) // name 필드 추가
            .build();

        return new CustomOAuth2User(memberDTO);
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
            // 새 사용자 생성
            member = Member.builder()
                .username(username)
                .name(oAuth2Response.getName())
                .email(oAuth2Response.getEmail())
                .role("ROLE_USER")
                .build();
        } else {
            // 기존 사용자 업데이트
            member.setEmail(oAuth2Response.getEmail());
            member.setName(oAuth2Response.getName());
        }

        // 사용자 정보를 데이터베이스에 저장
        return memberRepository.save(member);
    }
}
