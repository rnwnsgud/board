package store.ppingpong.board.post.service.port;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.ppingpong.board.post.domain.Post;

public interface PostRepository {
    Post create(Post post);
    Page<Post> findByForumId(String forumId, Integer listNum, Pageable pageable);

}
