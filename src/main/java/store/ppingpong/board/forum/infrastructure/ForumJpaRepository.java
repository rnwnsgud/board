package store.ppingpong.board.forum.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.ppingpong.board.forum.domain.Category;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.ForumStatus;

import java.util.List;
import java.util.Optional;

public interface ForumJpaRepository extends JpaRepository<ForumEntity, Long> {
    List<ForumEntity> findByForumStatus(ForumStatus forumStatus);
    Optional<ForumEntity> findByForumId(String forumId);
    @Modifying(clearAutomatically = true)
    @Query("update ForumEntity f " +
            "set f.introduction = :introduction, f.category = :category " +
            "where f.forumId = :forumId")
    void modify(@Param("forumId") String forumId, @Param("introduction") String introduction,
                @Param("category") Category category);
}
