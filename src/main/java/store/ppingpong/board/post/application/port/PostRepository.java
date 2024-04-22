package store.ppingpong.board.post.application.port;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.dto.PostWithWriter;

public interface PostRepository {
    Post create(Post post);
    Page<PostWithWriter> findByForumId(String forumId, Integer listNum, Pageable pageable);
    Post findById(long id);
    void inquiry(Post post);

}
