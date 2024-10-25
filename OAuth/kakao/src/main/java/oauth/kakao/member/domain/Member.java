package oauth.kakao.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oauth.kakao.member.domain.type.OAuthType;
import oauth.kakao.member.domain.type.Role;
import oauth.kakao.member.domain.type.Gender;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", length = 25)
    private String email;

    @Column(name = "name", length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_type", length = 20)
    private OAuthType oAuthType;

    @Column(name = "social_id", length = 100)
    private String socialId;


    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String email, String name, OAuthType oAuthType, String socialId,
                  Role role) {
        this.email = email;
        this.name = name;
        this.oAuthType = oAuthType;
        this.socialId = socialId;
        this.role = role;
    }



    public String getRoleKey() {
        return this.role.getKey();
    }

}