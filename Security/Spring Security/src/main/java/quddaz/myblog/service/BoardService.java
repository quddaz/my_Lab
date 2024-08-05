package quddaz.myblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quddaz.myblog.domain.Board;
import quddaz.myblog.domain.DTO.BoardFormDTO;
import quddaz.myblog.repository.BoardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardRepository boardRepository;


  public List<Board> getAllBoards() {
    return boardRepository.findAll();
  }

  public void createBoard(BoardFormDTO boardFormDTO) {
    Board board = new Board();
    board.setTitle(boardFormDTO.getTitle());
    board.setContent(boardFormDTO.getContent());
    boardRepository.save(board);
  }

  public Board updateBoard(Board board) {
    return boardRepository.save(board);
  }

  public void deleteBoard(Long id) {
    boardRepository.deleteById(id);
  }
}
