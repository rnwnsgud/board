package store.ppingpong.board.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.user.domain.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByLoginInfoLoginId(String loginId);

    @Transactional(readOnly = true)
    @Query("select u " +
            "from ForumManagerEntity fm " +
            "join UserEntity u on fm.userId = u.id " +
            "where fm.forumId = :forumId and fm.forumManagerLevel = 'MANAGER'")
    UserEntity findForumManager(@Param("forumId") String forumId);

    @Transactional(readOnly = true)
    @Query("select u " +
            "from ForumManagerEntity fm " +
            "join UserEntity u on fm.userId = u.id " +
            "where fm.forumId = :forumId and fm.forumManagerLevel = 'ASSISTANT'")
    List<UserEntity> findForumAssistants(@Param("forumId") String forumId);

    @Transactional(readOnly = true)
    @Query("select u " +
            "from ForumManagerEntity fm " +
            "join UserEntity u on fm.userId = u.id " +
            "where fm.forumId = :forumId and fm.forumManagerLevel != 'USER' and u.id = :userId")
    Optional<UserEntity> findManagerOrAssistant(@Param("forumId") String forumId, @Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Query("update UserEntity u " +
            "set u.lastLoginAt = :lastLoginAt " +
            "where u.id = :userId")
    void login(@Param("userId") long userId, @Param("lastLoginAt") Long lastLoginAt);

    @Modifying(clearAutomatically = true)
    @Query("update UserEntity u " +
            "set u.userStatus = :userStatus " +
            "where u.id = :userId")
    void verify(@Param("userId") long userId, @Param("userStatus") UserStatus userStatus);
}
