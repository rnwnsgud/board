package store.ppingpong.board.post.service.port;

import store.ppingpong.board.post.domain.Post;

public interface PostRepository {
    Post create(Post post);
}
