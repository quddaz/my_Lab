package quddaz.myblog.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
@Getter
@Setter
public class JoinDTO {
  @NotBlank(message = "이름은 필수 입력 값입니다.")
  @Length(min = 3,max = 16,message = "아이디를 8자 이상, 16자 이하로 입력해주세요")
  private String userName;

  @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
  @Length(min = 8,max = 16,message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
  private String password;
}
