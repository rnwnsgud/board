package store.ppingpong.board.post.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.ppingpong.board.post.dto.PostWithWriter;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {
    @Query(value = "select new store.ppingpong.board.post.dto.PostWithWriter(p.id, p.title, p.postType, u.userInfo.nickname, p.createdAt) " +
            "from PostEntity p " +
            "join UserEntity u on p.userId = u.id " +
            "where p.forumId = :forumId", countQuery = "select count(p) from PostEntity p where p.forumId = :forumId")
    Page<PostWithWriter> findByForumId(@Param("forumId") String forumId, Pageable pageable);
}
