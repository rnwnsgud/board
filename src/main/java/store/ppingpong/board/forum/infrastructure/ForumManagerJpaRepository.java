package store.ppingpong.board.forum.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ForumManagerJpaRepository extends JpaRepository<ForumManagerEntity, Long> {
    List<ForumManagerEntity> findByForumId(@Param("forumId") String forumId);

    @Query("select fm from ForumManagerEntity fm where fm.forumId = :forumId and fm.forumManagerLevel = 'MANAGER'")
    ForumManagerEntity findMangerByForumId(@Param("forumId") String forumId);

    void deleteByForumIdAndUserId(String forumId, long userId);

}
