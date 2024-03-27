package store.ppingpong.board.post.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findByForumId(String forumId, Pageable pageable);
}
