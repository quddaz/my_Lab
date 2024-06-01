package REST.API.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@Getter
public enum ErrorCode {
  USERNAME_DUPLICATED(HttpStatus.CONFLICT, "유저 중복");

  private HttpStatus httpStatus;
  private String message;
}
