package store.ppingpong.board.post.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import store.ppingpong.board.image.domain.FileExtension;
import store.ppingpong.board.image.domain.Image;
import store.ppingpong.board.mock.forum.FakeForumManagerRepository;
import store.ppingpong.board.mock.forum.TestClockLocalHolder;
import store.ppingpong.board.mock.image.FakeImageRepository;
import store.ppingpong.board.mock.image.FakeUploader;
import store.ppingpong.board.mock.post.FakePostRepository;
import store.ppingpong.board.mock.post.FakeReadPostRepository;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.domain.PostWithImages;
import store.ppingpong.board.post.dto.PostCreate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
                .imageRepository(new FakeImageRepository())
                .uploader(new FakeUploader())
                .clockLocalHolder(testClockLocalHolder)
                .build();
    }

    @Test
    void PostCreate로_Post를_생성할_수_있다() throws IOException {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("title")
                .content("conetent")
                .postTypeId(1L)
                .build();
        // when
        PostWithImages postWithImages = postService.create(postCreate, 1L, "reverse1999", null);

        // then
//        assertThat(postWithImages.getPostId()).isEqualTo(1L);
        assertThat(postWithImages.getForumId()).isEqualTo("reverse1999");
        assertThat(postWithImages.getTitle()).isEqualTo("title");
        assertThat(postWithImages.getPostTypeId()).isEqualTo(1L);
        assertThat(postWithImages.getCreatedAt()).isEqualTo(LocalDateTime.MIN);
    }

    @Test
    void Post생성시_유저가_포럼매니저가_아니라면_포럼매니저를_생성한다() throws IOException {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("title")
                .content("conetent")
                .postTypeId(1L)
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
                .postTypeId(1L)
                .build();
        PostWithImages postWithImages = postService.create(postCreate, 1L, "reverse1999", null);
        // when
        PostWithImages postWithImages1 = postService.findById(postWithImages.getPostId(), 2L);

        // then
        assertThat(postWithImages1.getVisitCount()).isEqualTo(1);
    }

    @Test
    void Id로_Post를_조회할_수_있지만_본인의것이면_조회수가_증가하지_않는다() throws IOException {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("title")
                .content("conetent")
                .postTypeId(1L)
                .build();
        PostWithImages postWithImages = postService.create(postCreate, 1L, "reverse1999", null);

        // when
        PostWithImages postWithImages1 = postService.findById(postWithImages.getPostId(), 1L);

        // then
        assertThat(postWithImages1.getVisitCount()).isEqualTo(0);
    }

    @Test
    void Post생성시_이미지도_첨부할_수_있다() throws IOException {
        String fileName = "이미지1";
        String contentType = "image/png";
        List<MultipartFile> multipartFiles = new ArrayList<>();
        MockMultipartFile multipartFile = new MockMultipartFile(
                "images",
                fileName+"."+"png",
                contentType,
                "image".getBytes()
                );
        MockMultipartFile multipartFile2 = new MockMultipartFile(
                "images",
                fileName+"."+"png",
                contentType,
                "image".getBytes()
        );
        multipartFiles.add(multipartFile);
        multipartFiles.add(multipartFile2);
        PostCreate postCreate = PostCreate.builder()
                .title("title")
                .content("conetent")
                .postTypeId(1L)
                .build();
        PostWithImages postWithImages = postService.create(postCreate, 1L, "reverse1999", multipartFiles);
        List<Image> images = postWithImages.getImages();
        assertThat(images.size()).isEqualTo(2);
        assertThat(images.get(0).getOriginalName()).isEqualTo(fileName+"."+"png");
        assertThat(images.get(0).getFileExtension()).isEqualTo(FileExtension.PNG);
    }


}
