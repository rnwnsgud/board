package store.ppingpong.board.forum.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.domain.ForumManagerLevel;

import java.util.List;

public interface ForumManagerJpaRepository extends JpaRepository<ForumManagerEntity, Long> {
    // For queries with named parameters you need to provide names for method parameters; Use @Param for query method parameters, or when on Java 8+ use the javac flag -parameters
    @Query("select fm from ForumManagerEntity fm where fm.forumEntity.forumId = :forumId")
    List<ForumManagerEntity> getListByForumId(@Param("forumId") String forumId);

    @Query("select fm from ForumManagerEntity fm where fm.forumEntity.forumId = :forumId and fm.forumManagerLevel = 'MANAGER'")
    ForumManagerEntity getByForumIdAndLevel(@Param("forumId") String forumId);
}
