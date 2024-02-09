package store.ppingpong.board.forum.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.ForumStatus;

import java.util.List;

public interface ForumJpaRepository extends JpaRepository<ForumEntity, Long> {
    List<ForumEntity> findByForumStatus(ForumStatus forumStatus);
}
