package store.ppingpong.board.post.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.ppingpong.board.mock.forum.TestClockLocalHolder;
import store.ppingpong.board.mock.post.FakePostRepository;
import store.ppingpong.board.mock.user.FakeUserRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class PostServiceTest {
    private PostService postService;

    @BeforeEach
    void init() {
        FakePostRepository fakePostRepository = new FakePostRepository();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        TestClockLocalHolder testClockLocalHolder = new TestClockLocalHolder(LocalDateTime.MIN);
        postService = PostService.builder()
                .postRepository(fakePostRepository)
                .clockLocalHolder(testClockLocalHolder)
                .build();
    }

}
