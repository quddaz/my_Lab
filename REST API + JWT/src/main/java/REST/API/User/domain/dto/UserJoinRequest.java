package REST.API.User.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserJoinRequest {
  @JsonProperty("userName")
  private String userName;

  @JsonProperty("password")
  private String password;
}
