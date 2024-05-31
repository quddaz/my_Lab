package quddaz.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quddaz.myblog.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
