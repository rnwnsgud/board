package store.ppingpong.board.post.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.service.port.PostRepository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final PostJpaRepository postJpaRepository;
    @Override
    public Post create(Post post) {
        return postJpaRepository.save(PostEntity.from(post)).toModel();
    }
}
