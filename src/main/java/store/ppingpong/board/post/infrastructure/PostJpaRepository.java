package store.ppingpong.board.post.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.ppingpong.board.post.domain.PostWithWriter;

import java.util.List;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {
    @Query(value = "select new store.ppingpong.board.post.domain.PostWithWriter(p.id, p.title, p.postTypeId, u.userInfo.nickname, p.createdAt, "+
            "case when (select count(rp) from ReadPostEntity rp where rp.postId = p.id and rp.userId = u.id) > 0 then true else false end) " +
            "from PostEntity p " +
            "join UserEntity u on p.userId = u.id " +
            "where p.forumId = :forumId " +
            "and (:searchHead is null or p.postTypeId = :searchHead)", countQuery = "select count(p) from PostEntity p where p.forumId = :forumId")
    Page<PostWithWriter> findPostAndUsernameByForumId(@Param("forumId") String forumId, @Param("searchHead") Long searchHead, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update PostEntity p " +
            "set p.visitCount = :visitCount " +
            "where p.id = :id")
    void inquiry(@Param("id") long id, @Param("visitCount") long visitCount);

    @Modifying(clearAutomatically=true)
    @Query("delete from PostEntity p where p.id = :id")
    int deleteById(@Param("id") long id);


    @Query("select new store.ppingpong.board.post.domain.PostWithWriter(p.id, p.title, p.postTypeId, u.userInfo.nickname, p.createdAt, "+
            "case when (select count(rp) from ReadPostEntity rp where rp.postId = p.id and rp.userId = u.id) > 0 then true else false end) " +
            "from PostEntity p " +
            "join UserEntity u on p.userId = u.id " +
            "where p.forumId = :forumId " +
            "and p.notice = true ")
    List<PostWithWriter> findByForumIdAndNoticeTrue(String forumId);


}
