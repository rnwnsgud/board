package store.ppingpong.board.mock.post;

import store.ppingpong.board.post.application.port.ReadPostRepository;
import store.ppingpong.board.post.domain.ReadPost;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeReadPostRepository implements ReadPostRepository {

    private final List<ReadPost> data = new ArrayList<>();

    @Override
    public ReadPost create(ReadPost readPost) {
        return null;
    }

    @Override
    public Optional<ReadPost> get(long userId, long postId) {
        return Optional.empty();
    }

    @Override
    public void delete(long postId) {

    }
}
