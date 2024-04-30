package store.ppingpong.board.image.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.ppingpong.board.image.domain.Image;

import java.util.List;

public interface ImageJpaRepository extends JpaRepository<ImageEntity, Long> {

    List<ImageEntity> findByPostId(@Param("postId") long postId);
    @Modifying(clearAutomatically=true)
    @Query("delete from ImageEntity i where i.postId = :postId")
    int deleteByPostId(@Param("postId") long postId);
}
