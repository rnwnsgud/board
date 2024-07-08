package store.ppingpong.board.post.domain;

import org.junit.jupiter.api.Test;
import store.ppingpong.board.common.handler.exception.resource.ResourceNotOwnerException;
import store.ppingpong.board.mock.forum.TestClockLocalHolder;
import store.ppingpong.board.post.dto.PostCreateRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class PostTest {

    @Test
    void PostCreate으로_Post를_생성할_수_있다() {
        // given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("title")
                .content("content")
                .postTypeId(1L)
                .build();
        // when
        Post post = Post.of(postCreateRequest, 1L, "reverse1999", new TestClockLocalHolder(LocalDateTime.MIN));

        assertThat(post.getId()).isNull();
        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getContent()).isEqualTo("content");
        assertThat(post.getPostTypeId()).isEqualTo(1L);
        assertThat(post.getCreatedAt()).isEqualTo(LocalDateTime.MIN);
        assertThat(post.getUserId()).isEqualTo(1L);
        assertThat(post.getForumId()).isEqualTo("reverse1999");
        assertThat(post.getLastModifiedAt()).isNull();

    }

    @Test
    void Post_방문시_조회수가_증가한다() {
        // given
        long myId = 1L;
        Post beforePost = Post.builder()
                .userId(myId)
                .visitCount(0)
                .build();
        // when
        Post afterPost = beforePost.visit();
        // then
        assertThat(afterPost.getVisitCount()).isEqualTo(1L);
    }

    @Test
    void Post_소유자를_가려낼_수_있다() {
        // given
        Post post = Post.builder()
                .id(1L)
                .userId(1L)
                .build();
        // when
        post.checkPostOwner(1L);
    }

    @Test
    void Post_소유자가_아닐_시_예외가_발생한다() {
        // given
        Post post = Post.builder()
                .id(1L)
                .userId(1L)
                .build();
        // when
        assertThatThrownBy(() -> post.checkPostOwner(2L))
                .isInstanceOf(ResourceNotOwnerException.class);
    }


}
