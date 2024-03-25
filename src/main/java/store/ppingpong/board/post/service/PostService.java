package store.ppingpong.board.post.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.common.service.port.ClockLocalHolder;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.dto.PostCreate;
import store.ppingpong.board.post.service.port.PostRepository;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.service.port.UserRepository;

@Builder
@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ClockLocalHolder clockLocalHolder;

    public Post create(PostCreate postCreate, Long userId, String forumId) {
        User user = userRepository.getById(userId);
        user.isActive();
        Post post = Post.of(postCreate, userId, forumId, clockLocalHolder);
        return postRepository.create(post);
    }
}
