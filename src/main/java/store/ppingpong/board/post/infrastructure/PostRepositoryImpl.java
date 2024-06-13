package store.ppingpong.board.post.infrastructure;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.common.handler.exception.resource.ResourceNotFoundException;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.domain.PostWithWriter;
import store.ppingpong.board.post.application.port.PostRepository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final PostJpaRepository postJpaRepository;

    @Override
    public Post create(Post post) {
        return postJpaRepository.save(PostEntity.from(post)).toModel();
    }

    @Override
    public Page<PostWithWriter> findByForumId(String forumId, Integer listNum, Long search_head, Pageable pageable) {
        int pageSize;
        if (listNum == null || listNum >= 10) pageSize = 10;
        else pageSize = listNum;
        int pageNumber = pageable.getPageNumber();
        if (pageNumber <=0) pageNumber = 0;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return postJpaRepository.findPostAndUsernameByForumId(forumId, search_head, pageRequest);
    }

    @Override
    public Post getById(long id) {
        return postJpaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", id)).toModel();
    }

    @Override
    public void inquiry(Post post) {
        postJpaRepository.inquiry(post.getId(), post.getVisitCount());
    }

    @Override
    public int delete(long id) {
        return postJpaRepository.deleteById(id);
    }

    @Override
    public Post modify(Post post) {
        return postJpaRepository.save(PostEntity.modify(post)).toModel();
    }

}
