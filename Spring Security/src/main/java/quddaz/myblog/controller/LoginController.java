package quddaz.myblog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import quddaz.myblog.domain.DTO.JoinDTO;
import quddaz.myblog.domain.Member;
import quddaz.myblog.service.MemberService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

  private final MemberService memberService;

  @GetMapping("/login")
  public String loginPage(){
    return "login";
  }
  @GetMapping("/main")
  public String mainPage(Model model){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    model.addAttribute("userName", userName);
    return "main";
  }
  @GetMapping("/join")
  public String joinPage(Model model){
    model.addAttribute("joinDTO", new JoinDTO());
    return "join";
  }

  @PostMapping("/join")
  public String joinProc(@Valid @ModelAttribute JoinDTO joinDTO, BindingResult bindingResult){
    // 아이디 중복 체크
    if(memberService.checkDuplicateUsername(joinDTO.getUserName())) {
      bindingResult.reject("userName", "아이디가 중복됩니다.");
    }

    // 유효성 검사 및 오류 처리
    if (bindingResult.hasErrors()) {
      log.info("errors={}", bindingResult);
      return "join";
    }

    // 중복 아이디가 없는 경우 회원가입 처리
    Member member = new Member();
    member.setUserName(joinDTO.getUserName());
    member.setPassword(joinDTO.getPassword());
    memberService.join(member);

    return "redirect:/login"; // 로그인 페이지로 리다이렉트
  }
}
