package store.ppingpong.board.post.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.common.service.port.ClockLocalHolder;
import store.ppingpong.board.forum.service.port.ForumManagerRepository;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.dto.PostCreate;
import store.ppingpong.board.post.dto.PostWithWriter;
import store.ppingpong.board.post.service.port.PostRepository;


@Builder
@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final PostRepository postRepository;
    private final ForumManagerRepository forumManagerRepository;
    private final ClockLocalHolder clockLocalHolder;

    public Post create(PostCreate postCreate, Long userId, String forumId) {
        forumManagerRepository.findForumUserOrCreate(forumId, userId).isAccessible();
        return postRepository.create(Post.of(postCreate, userId, forumId, clockLocalHolder));
    }

    public Page<PostWithWriter> getList(String forumId, int listNum, Pageable pageable) {
        return postRepository.findByForumId(forumId, listNum, pageable);
    }

}
