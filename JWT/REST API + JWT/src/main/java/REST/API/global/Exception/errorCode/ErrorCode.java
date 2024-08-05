package REST.API.global.Exception.errorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public interface ErrorCode {
  HttpStatus getHttpStatus();
  String getMessage();
}
