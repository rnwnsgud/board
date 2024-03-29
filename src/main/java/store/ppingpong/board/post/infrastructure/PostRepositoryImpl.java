package store.ppingpong.board.post.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.dto.PostWithWriter;
import store.ppingpong.board.post.service.port.PostRepository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final PostJpaRepository postJpaRepository;
    @Override
    public Post create(Post post) {
        return postJpaRepository.save(PostEntity.from(post)).toModel();
    }

    @Override
    public Page<PostWithWriter> findByForumId(String forumId, Integer listNum, Pageable pageable) {
        int pageSize;
        if (listNum == null || listNum >= 10) pageSize = 10;
        else pageSize = listNum;
        int pageNumber = pageable.getPageNumber();
        if (pageNumber <=0) pageNumber = 0;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return postJpaRepository.findPostAndUsernameByForumId(forumId, pageRequest);
    }
}
