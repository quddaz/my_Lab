package REST.API.User.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("password")
    private String password;
}
