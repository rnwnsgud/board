package store.ppingpong.board.mock.forum;

import lombok.Builder;
import store.ppingpong.board.common.service.port.ClockLocalHolder;
import store.ppingpong.board.forum.controller.ForumController;
import store.ppingpong.board.forum.service.ForumService;
import store.ppingpong.board.forum.service.port.ForumRepository;
import store.ppingpong.board.forum.service.port.ForumManagerRepository;
import store.ppingpong.board.mock.post.FakePostRepository;
import store.ppingpong.board.post.service.PostService;

public class TestForumContainer {

    public final ForumRepository forumRepository;
    public final ForumManagerRepository forumUserRepository;
    public final ForumController forumController;


    @Builder
    public TestForumContainer(ClockLocalHolder clockLocalHolder) {
        this.forumRepository = new FakeForumRepository();
        this.forumUserRepository = new FakeForumManagerRepository();
        ForumService forumService = ForumService.builder()
                .forumRepository(forumRepository)
                .forumManagerRepository(forumUserRepository)
                .clockLocalHolder(clockLocalHolder)
                .build();
        PostService postService = PostService.builder()
                .postRepository(new FakePostRepository())
                .forumManagerRepository(forumUserRepository)
                .clockLocalHolder(clockLocalHolder)
                .build();
        this.forumController = ForumController.builder()
                .forumService(forumService)
                .postService(postService)
                .build();
    }
}
