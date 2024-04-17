package store.ppingpong.board.post.service.port;


import store.ppingpong.board.post.domain.ReadPost;

import java.util.Optional;

public interface ReadPostRepository {
    ReadPost create(ReadPost readPost);
    Optional<ReadPost> get(long userId, long postId);
}
