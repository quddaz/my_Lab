package REST.API.global.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto<T> {
  private int code;
  private String message;
  private T data;
  private LocalDateTime timestamp;

  public static <T> ResponseDto<T> of(T data){
    return new ResponseDto<>(HttpStatus.OK.value(), null, data, LocalDateTime.now());
  }
  public static <T> ResponseDto<T> fail(Integer status, String message) {
    return new ResponseDto<>(status, message, null, LocalDateTime.now());
  }
}
