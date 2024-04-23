package store.ppingpong.board.post.application;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import store.ppingpong.board.mock.forum.FakeForumManagerRepository;
import store.ppingpong.board.mock.forum.TestClockLocalHolder;
import store.ppingpong.board.mock.post.FakePostRepository;
import store.ppingpong.board.mock.post.FakeReadPostRepository;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.domain.PostType;
import store.ppingpong.board.post.domain.PostWithImages;
import store.ppingpong.board.post.dto.PostCreate;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@RequiredArgsConstructor
public class PostServiceTest {

    private PostService postService;
    private FakeForumManagerRepository fakeForumManagerRepository;
    private FakeReadPostRepository fakeReadPostRepository;

    @BeforeEach
    void init() {
        FakePostRepository fakePostRepository = new FakePostRepository();
        fakeForumManagerRepository = new FakeForumManagerRepository();
        fakeReadPostRepository = new FakeReadPostRepository();
        TestClockLocalHolder testClockLocalHolder = new TestClockLocalHolder(LocalDateTime.MIN);
        postService = PostService.builder()
                .postRepository(fakePostRepository)
                .forumManagerRepository(fakeForumManagerRepository)
                .readPostService(new ReadPostService(fakeReadPostRepository))
                .clockLocalHolder(testClockLocalHolder)
                .build();
    }

    @Test
    void PostCreate로_Post를_생성할_수_있다() throws IOException {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("title")
                .content("conetent")
                .postType(PostType.COMMON)
                .build();
        // when
        PostWithImages postWithImages = postService.create(postCreate, 1L, "reverse1999", null);

        // then
//        assertThat(postWithImages.getPostId()).isEqualTo(1L);
        assertThat(postWithImages.getForumId()).isEqualTo("reverse1999");
        assertThat(postWithImages.getTitle()).isEqualTo("title");
        assertThat(postWithImages.getPostType()).isEqualTo(PostType.COMMON);
        assertThat(postWithImages.getCreatedAt()).isEqualTo(LocalDateTime.MIN);
    }

    @Test
    void Post생성시_유저가_포럼매니저가_아니라면_포럼매니저를_생성한다() throws IOException {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("title")
                .content("conetent")
                .postType(PostType.COMMON)
                .build();
        // when
        postService.create(postCreate, 1L, "reverse1999", null);
        // then
        assertThat(fakeForumManagerRepository.findManagerByForumId("reverse1999")).isNotNull();

    }

    @Test
    void Id로_Post를_조회할_수_있고_조회수를_증가시킨다() throws IOException {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("title")
                .content("conetent")
                .postType(PostType.COMMON)
                .build();
        PostWithImages postWithImages = postService.create(postCreate, 1L, "reverse1999", null);
        // when
        Post post = postService.findById(postWithImages.getPostId(), 2L);

        // then
        assertThat(post.getVisitCount()).isEqualTo(1);
    }

    @Test
    void Id로_Post를_조회할_수_있지만_본인의것이면_조회수가_증가하지_않는다() throws IOException {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("title")
                .content("conetent")
                .postType(PostType.COMMON)
                .build();
        postService.create(postCreate, 1L, "reverse1999", null);

        // when
        Post post = postService.findById(1L, 1L);

        // then
        assertThat(post.getVisitCount()).isEqualTo(0);
    }


}