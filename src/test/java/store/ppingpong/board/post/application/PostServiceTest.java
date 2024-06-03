package store.ppingpong.board.post.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.image.domain.FileExtension;
import store.ppingpong.board.image.domain.Image;
import store.ppingpong.board.mock.forum.FakeForumManagerRepository;
import store.ppingpong.board.mock.forum.TestClockLocalHolder;
import store.ppingpong.board.mock.image.FakeImageRepository;
import store.ppingpong.board.mock.image.FakeUploader;
import store.ppingpong.board.mock.post.FakePostRepository;
import store.ppingpong.board.mock.post.FakeReadPostRepository;
import store.ppingpong.board.mock.reaction.FakeReactionRepository;
import store.ppingpong.board.post.domain.PostWithImages;
import store.ppingpong.board.post.domain.service.PostCreator;
import store.ppingpong.board.post.dto.PostCreateRequest;
import store.ppingpong.board.post.dto.PostCreateResponse;
import store.ppingpong.board.post.dto.PostDeleteResponse;
import store.ppingpong.board.user.domain.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class PostServiceTest {

    private PostService postService;
    private FakeForumManagerRepository fakeForumManagerRepository;
    // TODO 리액션 관련 테스트 추가

    @BeforeEach
    void init() {
        FakePostRepository fakePostRepository = new FakePostRepository();
        fakeForumManagerRepository = new FakeForumManagerRepository();
        FakeReadPostRepository fakeReadPostRepository = new FakeReadPostRepository();
        TestClockLocalHolder testClockLocalHolder = new TestClockLocalHolder(LocalDateTime.MIN);
        postService = PostService.builder()
                .postRepository(fakePostRepository)
                .forumManagerRepository(fakeForumManagerRepository)
                .readPostService(new ReadPostService(fakeReadPostRepository))
                .postCreator(new PostCreator(fakeForumManagerRepository, fakePostRepository, testClockLocalHolder))
                .imageRepository(new FakeImageRepository())
                .uploader(new FakeUploader())
                .reactionRepository(new FakeReactionRepository())
                .clockLocalHolder(testClockLocalHolder)
                .build();
    }

    @Test
    void PostCreate로_Post를_생성할_수_있다() throws IOException {
        // given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("title")
                .content("conetent")
                .postTypeId(1L)
                .build();
        // when
        PostCreateResponse postCreateResponse = postService.create(postCreateRequest, 1L, "reverse1999", null);

        // then
        assertThat(postCreateResponse.getForumId()).isEqualTo("reverse1999");
        assertThat(postCreateResponse.getTitle()).isEqualTo("title");
        assertThat(postCreateResponse.getPostTypeId()).isEqualTo(1L);
        assertThat(postCreateResponse.getCreatedAt()).isEqualTo(LocalDateTime.MIN);
    }

    @Test
    void Post생성시_유저가_포럼매니저가_아니라면_포럼매니저를_생성한다() throws IOException {
        // given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("title")
                .content("conetent")
                .postTypeId(1L)
                .build();
        // when
        postService.create(postCreateRequest, 1L, "reverse1999", null);
        // then
        assertThat(fakeForumManagerRepository.findManagerByForumId("reverse1999")).isNotNull();

    }

    @Test
    void Id로_Post를_조회할_수_있고_조회수를_증가시킨다() throws IOException {

        // given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("title")
                .content("conetent")
                .postTypeId(1L)
                .build();
        User user = User.valueOf(2L, "USER");
        LoginUser loginUser = new LoginUser(user);

        PostCreateResponse postCreateResponse = postService.create(postCreateRequest, 1L, "reverse1999", null);
        // when
        PostWithImages postWithImages1 = postService.getById(postCreateResponse.getPostId(), loginUser);

        // then
        assertThat(postWithImages1.getVisitCount()).isEqualTo(1);
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
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("title")
                .content("conetent")
                .postTypeId(1L)
                .build();
        PostCreateResponse postCreateResponse = postService.create(postCreateRequest, 1L, "reverse1999", multipartFiles);
        List<Image> images = postCreateResponse.getImages();
        for (Image image : images) {
            System.out.println(image.getOriginalName());
        }
        assertThat(images.size()).isEqualTo(2);
        assertThat(images.get(0).getOriginalName()).isEqualTo(fileName+"."+"png");
        assertThat(images.get(0).getFileExtension()).isEqualTo(FileExtension.PNG);
    }

    @Test
    void Post는_User의_방문사실을_기록할_수_있다() throws IOException {
        // given
        LoginUser loginUser = new LoginUser(User.valueOf(1L, "USER"));
        LoginUser loginUser2 = new LoginUser(User.valueOf(2L, "USER"));

        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("title")
                .content("conetent")
                .postTypeId(1L)
                .build();
        PostCreateResponse postCreateResponse = postService.create(postCreateRequest, 1L, "reverse1999", null);
        // when
        postService.getById(postCreateResponse.getPostId(), loginUser);
        PostWithImages postWithImages = postService.getById(postCreateResponse.getPostId(), loginUser2);

        // then
        assertThat(postWithImages.getVisitCount()).isEqualTo(2);
    }

    @Test
    void Post의_소유자는_본인의_게시글을_삭제할_수_있다() throws IOException {
        // given
        List<MultipartFile> multipartFiles = new ArrayList<>();
        String fileName = "이미지1";
        String contentType = "image/png";
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
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("title")
                .content("conetent")
                .postTypeId(1L)
                .build();
        PostCreateResponse postCreateResponse = postService.create(postCreateRequest, 1L, "reverse1999", multipartFiles);
        // when
        PostDeleteResponse postDeleteResponse = postService.delete(postCreateResponse.getPostId(), 1L);

        // then
        assertThat(postDeleteResponse.getStatus()).isEqualTo(1);
        assertThat(postDeleteResponse.getDeletedImageCount()).isEqualTo(2);
    }


}
