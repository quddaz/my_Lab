package REST.API.global.Exception.handler;

import REST.API.global.Exception.BaseException.AppException;
import REST.API.global.Exception.errorCode.ErrorCode;
import REST.API.global.Response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {
  @ExceptionHandler(AppException.class)
  public ResponseEntity<ResponseDto<?>> appExceptionHandler(AppException e) {
    ErrorCode errorCode = e.getErrorCode();
    ResponseDto<?> response = ResponseDto.fail(errorCode.getHttpStatus().value(), errorCode.getMessage() + " " + e.getMessage());
    return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
  }
  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ResponseDto<?>> usernameNotFoundExceptionHandler(String message) {
    ResponseDto<?> response = ResponseDto.fail(500, message);
    return ResponseEntity.status(500).body(response);
  }
}
