package REST.API.ControllerTest;

import REST.API.User.Service.UserService;
import REST.API.User.domain.dto.UserJoinRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserControllerTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  UserService userService;
  @Autowired
  ObjectMapper objectMapper;
  @Test
  @DisplayName("회원가입 성공")
  void join() throws Exception{
    String userName = "KIM";
    String password = "1q2w3e4r";
    mockMvc.perform(post("/api/v1/users/join")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName,password))))
        .andDo(print())
        .andExpect(status().isOk());
  }
  @Test
  @DisplayName("회원가입 실패 - userName 중복")
  void join_fail() throws Exception{
    String userName = "KIM";
    String password = "1q2w3e4r";

    mockMvc.perform(post("/api/v1/users/join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName,password))))
        .andDo(print())
        .andExpect(status().isConflict());
  }

  @Test
  @DisplayName("로그인 성공")
  void login_success() throws Exception{
    String userName = "KIM";
    String password = "1q2w3e4r";
    mockMvc.perform(post("/api/v1/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName,password))))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
