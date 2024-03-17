package store.ppingpong.board.forum.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.domain.ForumManagerLevel;
import store.ppingpong.board.mock.forum.FakeForumManagerRepository;

import static org.assertj.core.api.Assertions.*;

public class ForumManagerServiceTest {
    private ForumManagerService forumManagerService;

    @BeforeEach
    void init() {
        FakeForumManagerRepository fakeForumManagerRepository = new FakeForumManagerRepository();
        this.forumManagerService = ForumManagerService.builder()
                .forumManagerRepository(fakeForumManagerRepository)
                .build();

        ForumManager forumManager = ForumManager.of("리버스1999", 1L, ForumManagerLevel.MANAGER);
        fakeForumManagerRepository.save(forumManager);
    }

    @Test
    void forumId로_ForumManger를_찾을_수_있다() {
        // given
        String forumId = "리버스1999";

        // when
        ForumManager forumManager = forumManagerService.findByForumId(forumId);

        // then
        assertThat(forumManager.getForumId()).isEqualTo(forumId);
        assertThat(forumManager.getForumManagerLevel()).isEqualTo(ForumManagerLevel.MANAGER);
        assertThat(forumManager.getUserId()).isEqualTo(1L);

    }
}
