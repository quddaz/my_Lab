package REST.API.global.Exception.handler;

import REST.API.global.Exception.BaseException.AppException;
import REST.API.global.Exception.errorCode.ErrorCode;
import REST.API.global.Response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e){
    return  ResponseEntity.status(HttpStatus.CONFLICT)
        .body(e.getMessage());
  }
  @ExceptionHandler(AppException.class)
  public ResponseEntity<ResponseDto<?>> appExceptionHandler(AppException e) {
    ErrorCode errorCode = e.getErrorCode();
    ResponseDto<?> response = ResponseDto.fail(errorCode.getHttpStatus().value(), errorCode.getMessage() + " " + e.getMessage());
    return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
  }
}
