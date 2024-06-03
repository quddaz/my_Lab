package REST.API.global.Exception.BaseException;

import REST.API.global.Exception.errorCode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException {
  private ErrorCode errorCode;
}

