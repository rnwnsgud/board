package store.ppingpong.board.post.application.port;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.domain.PostWithWriter;

public interface PostRepository {
    Post create(Post post);
    Page<PostWithWriter> findByForumId(String forumId, Integer listNum, Long search_head, Pageable pageable);
    Post getById(long id);
    void inquiry(Post post);
    int delete(long id);

}
