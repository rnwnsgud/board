package store.ppingpong.board.post.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.mock.forum.FakeForumManagerRepository;
import store.ppingpong.board.mock.forum.TestClockLocalHolder;
import store.ppingpong.board.mock.post.FakePostRepository;
import store.ppingpong.board.mock.user.FakeUserRepository;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.domain.PostType;
import store.ppingpong.board.post.dto.PostCreate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@RequiredArgsConstructor
public class PostServiceTest {

    private PostService postService;
    private FakeForumManagerRepository fakeForumManagerRepository;

    @BeforeEach
    void init() {
        FakePostRepository fakePostRepository = new FakePostRepository();
        fakeForumManagerRepository = new FakeForumManagerRepository();
        TestClockLocalHolder testClockLocalHolder = new TestClockLocalHolder(LocalDateTime.MIN);
        postService = PostService.builder()
                .postRepository(fakePostRepository)
                .forumManagerRepository(fakeForumManagerRepository)
                .clockLocalHolder(testClockLocalHolder)
                .build();
    }

    @Test
    void PostCreate로_Post를_생성할_수_있다() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("title")
                .content("conetent")
                .postType(PostType.COMMON)
                .build();
        // when
        Post post = postService.create(postCreate, 1L, "reverse1999");

        // then
        assertThat(post.getId()).isEqualTo(1L);
        assertThat(post.getForumId()).isEqualTo("reverse1999");
        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getPostType()).isEqualTo(PostType.COMMON);
        assertThat(post.getCreatedAt()).isEqualTo(LocalDateTime.MIN);
        assertThat(post.getLastModifiedAt()).isNull();
    }

    @Test
    void Post생성시_유저가_포럼매니저가_아니라면_포럼매니저를_생성한다() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("title")
                .content("conetent")
                .postType(PostType.COMMON)
                .build();
        // when
        postService.create(postCreate, 1L, "reverse1999");
        // then
        assertThat(fakeForumManagerRepository.findManagerByForumId("reverse1999")).isNotNull();

    }





}
