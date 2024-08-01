package JWT.OAuth20.global.jwt.refresh.repository;

import JWT.OAuth20.global.jwt.refresh.entity.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RefreshRepository extends JpaRepository<Refresh, Long> {

    Boolean existsByRefresh(String refresh);
    Boolean existsByUsername(String username);
    @Transactional
    void deleteByRefresh(String refresh);
    @Transactional
    void deleteByUsername(String username);
}