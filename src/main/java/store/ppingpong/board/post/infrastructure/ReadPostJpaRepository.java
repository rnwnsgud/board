package store.ppingpong.board.post.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReadPostJpaRepository extends JpaRepository<ReadPostEntity, Long> {

    Optional<ReadPostEntity> findByUserIdAndPostId(long userId, long postId);

    @Modifying(clearAutomatically=true)
    @Query("delete from ReadPostEntity rp where rp.postId = :postId")
    void deleteByPostId(@Param("postId") long postId);
}
