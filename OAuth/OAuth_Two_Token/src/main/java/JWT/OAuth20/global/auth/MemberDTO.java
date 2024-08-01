package JWT.OAuth20.global.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    private String role;
    private String username;
    private String name;
    private String email;
}
