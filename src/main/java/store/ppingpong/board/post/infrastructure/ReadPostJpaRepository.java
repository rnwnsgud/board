package store.ppingpong.board.post.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReadPostJpaRepository extends JpaRepository<ReadPostEntity, Long> {

    Optional<ReadPostEntity> findByUserIdAndPostId(long userId, long postId);
}
