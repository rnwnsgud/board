package store.ppingpong.board.post.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.common.domain.ClockLocalHolder;
import store.ppingpong.board.forum.application.port.ForumManagerRepository;
import store.ppingpong.board.post.application.port.PostRepository;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.dto.PostCreateRequest;

@RequiredArgsConstructor
@Component
public class PostCreator {

    private final ForumManagerRepository forumManagerRepository;
    private final PostRepository postRepository;
    private final ClockLocalHolder clockLocalHolder;

    @Transactional
    public Post create(PostCreateRequest postCreateRequest, Long userId, String forumId) {
        forumManagerRepository.findForumUserOrCreate(forumId, userId).isAccessible();
        return postRepository.create(Post.of(postCreateRequest, userId, forumId, clockLocalHolder));
    }

}
