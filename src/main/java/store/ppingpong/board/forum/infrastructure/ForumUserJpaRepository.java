package store.ppingpong.board.forum.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumUserJpaRepository extends JpaRepository<ForumUserEntity, Long> {
}
