package store.ppingpong.board.forum.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import store.ppingpong.board.forum.domain.Forum;

public interface ForumJpaRepository extends JpaRepository<ForumEntity, Long> {

}
