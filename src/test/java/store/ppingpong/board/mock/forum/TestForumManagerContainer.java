package store.ppingpong.board.mock.forum;

import lombok.Builder;
import store.ppingpong.board.forum.controller.ForumManagerController;
import store.ppingpong.board.forum.application.ForumManagerService;
import store.ppingpong.board.forum.application.port.ForumManagerRepository;
import store.ppingpong.board.mock.user.FakeUserRepository;
import store.ppingpong.board.user.application.port.UserRepository;

public class TestForumManagerContainer {

    public final ForumManagerController forumManagerController;
    public final ForumManagerRepository forumManagerRepository;
    public final UserRepository userRepository;

    @Builder
    public TestForumManagerContainer() {
        this.forumManagerRepository = new FakeForumManagerRepository();
        this.userRepository = new FakeUserRepository();

        ForumManagerService forumManagerService = ForumManagerService.builder()
                .forumManagerRepository(forumManagerRepository)
                .build();
        this.forumManagerController = ForumManagerController.builder()
                .forumManagerService(forumManagerService)
                .userRepository(userRepository)
                .build();
    }
}
