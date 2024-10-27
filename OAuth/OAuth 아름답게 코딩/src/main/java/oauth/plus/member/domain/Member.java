package oauth.plus.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oauth.plus.member.domain.type.OAuthType;
import oauth.plus.member.domain.type.Role;
import oauth.plus.member.domain.type.Gender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Table(name = "member")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements OAuth2User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "email", length = 25)
    private String email;

    @Column(name = "name", length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_type", length = 20)
    private OAuthType oAuthType;

    @Column(name = "social_id", length = 100)
    private String socialId;
    @Column(name = "profile_image", length = 200)
    private String profile_image;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String email, String name, OAuthType oAuthType, String socialId, Role role, String profile_image) {
        this.email = email;
        this.name = name;
        this.oAuthType = oAuthType;
        this.socialId = socialId;
        this.role = role;
        this.profile_image = profile_image;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    @Override
    public Map<String, Object> getAttributes() {
        // 필요한 속성 맵을 반환하도록 구현
        return Map.of(
            "email", this.email,
            "name", this.name,
            "socialId", this.socialId,
            "oauthType", this.oAuthType,
            "profile_image", this.profile_image
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한 정보를 반환하도록 구현
        return List.of(new SimpleGrantedAuthority(this.role.getKey()));
    }
}