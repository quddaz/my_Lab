package quddaz.myblog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import quddaz.myblog.domain.DTO.BoardFormDTO;
import quddaz.myblog.service.BoardService;

@Controller
@RequiredArgsConstructor
public class BoardController {
  private final BoardService boardService;

  @GetMapping("/board")
  public String boardMain(Model model){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    model.addAttribute("name", name);
    model.addAttribute("boardList", boardService.getAllBoards());
    return "board";
  }
  @GetMapping("/board/create")
  public String createBoard(Model model){
    model.addAttribute("boardFormDTO",new BoardFormDTO());
    return "createboard";
  }
  @PostMapping("/board/create")
  public String createBoard(@Valid @ModelAttribute("boardFormDTO") BoardFormDTO boardFormDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "createboard"; // Return back to the form if there are validation errors
    }
    boardService.createBoard(boardFormDTO);
    return "redirect:/board"; // Redirect to the board list page after successful creation
  }
}
