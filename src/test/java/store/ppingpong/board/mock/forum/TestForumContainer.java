package store.ppingpong.board.mock.forum;

import lombok.Builder;
import store.ppingpong.board.common.service.port.ClockHolder;
import store.ppingpong.board.forum.controller.ForumController;
import store.ppingpong.board.forum.service.ForumServiceImpl;
import store.ppingpong.board.forum.service.port.ForumRepository;
import store.ppingpong.board.forum.service.port.ForumUserRepository;

public class TestForumContainer {

    public final ForumRepository forumRepository;
    public final ForumUserRepository forumUserRepository;
    public final ForumController forumController;

    @Builder
    public TestForumContainer(ClockHolder clockHolder) {
        this.forumRepository = new FakeForumRepository();
        this.forumUserRepository = new FakeForumUserRepository();

        ForumServiceImpl forumService = ForumServiceImpl.builder()
                .forumRepository(forumRepository)
                .forumUserRepository(forumUserRepository)
                .clockHolder(clockHolder)
                .build();
        this.forumController = ForumController.builder()
                .forumService(forumService)
                .build();
    }
}
