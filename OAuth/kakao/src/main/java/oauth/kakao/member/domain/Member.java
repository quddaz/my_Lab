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

@Table(name = "member")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member{

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

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String email, String name, OAuthType oAuthType, String socialId, Gender gender, LocalDate birthDate,
                  Role role) {
        this.email = email;
        this.name = name;
        this.oAuthType = oAuthType;
        this.socialId = socialId;
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = role;
    }

    public void uploadMember(String name, Gender gender, LocalDate birthDate) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}