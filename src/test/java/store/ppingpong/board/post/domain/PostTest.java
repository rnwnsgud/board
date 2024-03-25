package store.ppingpong.board.post.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import store.ppingpong.board.mock.forum.TestClockLocalHolder;
import store.ppingpong.board.post.dto.PostCreate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class PostTest {

    @Test
    void PostCreate으로_Post를_생성할_수_있다() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("title")
                .content("content")
                .postType(PostType.COMMON)
                .build();
        // when
        Post post = Post.of(postCreate, 1L, "reverse1999", new TestClockLocalHolder(LocalDateTime.MIN));

        assertThat(post.getId()).isNull();
        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getContent()).isEqualTo("content");
        assertThat(post.getPostType()).isEqualTo(PostType.COMMON);
        assertThat(post.getCreatedAt()).isEqualTo(LocalDateTime.MIN);
        assertThat(post.getUserId()).isEqualTo(1L);
        assertThat(post.getForumId()).isEqualTo("reverse1999");
        assertThat(post.getLastModifiedAt()).isNull();

    }
}
