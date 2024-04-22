package store.ppingpong.board.mock.post;

import store.ppingpong.board.common.domain.ClockLocalHolder;
import store.ppingpong.board.mock.user.FakeUserRepository;
import store.ppingpong.board.post.controller.PostController;
import store.ppingpong.board.post.application.PostService;
import store.ppingpong.board.post.application.port.PostRepository;
import store.ppingpong.board.user.application.port.UserRepository;

public class TestPostContainer {
    public final PostRepository postRepository;
    public final UserRepository userRepository;
    public final PostController postController;

    public TestPostContainer(ClockLocalHolder clockLocalHolder) {
        this.postRepository = new FakePostRepository();
        this.userRepository = new FakeUserRepository();
        PostService postService = PostService.builder()
                .postRepository(postRepository)
                .clockLocalHolder(clockLocalHolder)
                .build();
        this.postController = PostController.builder()
                .postService(postService)
                .build();
    }
}
