package store.ppingpong.board.forum.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import store.ppingpong.board.common.handler.exception.ResourceNotSameException;
import store.ppingpong.board.user.domain.User;

import java.time.LocalDateTime;

public class ForumManagerTest {
    @Test
    void 로그인한_사용자가_Forum의_MANAGER면_통과한다() {
        // given
        User user = User.valueOf(1L, "USER");

        ForumManager forumManager = ForumManager.of("reverse1999", 1L, ForumManagerLevel.MANAGER);
        // when
        forumManager.isOwnerOfForum(user);
    }

    @Test
    void 로그인한_사용자가_Forum의_MANAGER가_아니라면_예외를_던진다() {
        // given
        User user = User.valueOf(1L, "USER");

        ForumManager forumManager = ForumManager.of("reverse1999", 2L, ForumManagerLevel.MANAGER);
        // when
        Assertions.assertThatThrownBy(()->forumManager.isOwnerOfForum(user))
                .isInstanceOf(ResourceNotSameException.class);
    }
}
