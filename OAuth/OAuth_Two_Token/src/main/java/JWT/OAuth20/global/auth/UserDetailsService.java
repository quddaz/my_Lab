package JWT.OAuth20.global.auth;

import JWT.OAuth20.entity.Member;
import JWT.OAuth20.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = memberRepository.findByUsername(username);
        MemberDTO memberDTO = MemberDTO.builder()
            .username(member.getUsername())
            .email(member.getEmail())
            .name(member.getName())
            .build();
        return new CustomOAuth2User(memberDTO);
    }
}