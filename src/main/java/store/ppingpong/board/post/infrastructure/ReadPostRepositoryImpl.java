package store.ppingpong.board.post.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.post.domain.ReadPost;
import store.ppingpong.board.post.service.port.ReadPostRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ReadPostRepositoryImpl implements ReadPostRepository {

    private final ReadPostJpaRepository readPostJpaRepository;

    @Override
    public ReadPost create(ReadPost readPost) {
        return readPostJpaRepository.save(ReadPostEntity.from(readPost)).toModel();
    }

    @Override
    public Optional<ReadPost> get(long userId, long postId) {
        return readPostJpaRepository.findByUserIdAndPostId(userId, postId).map(ReadPostEntity::toModel);
    }
}
