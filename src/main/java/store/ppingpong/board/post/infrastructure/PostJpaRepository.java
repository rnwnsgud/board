package store.ppingpong.board.post.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import store.ppingpong.board.post.domain.Post;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {

}
