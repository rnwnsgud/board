package store.ppingpong.board.forum.domain;

import org.junit.jupiter.api.Test;
import store.ppingpong.board.forum.dto.ForumCreate;
import store.ppingpong.board.forum.dto.ForumUpdate;
import store.ppingpong.board.mock.forum.TestClockLocalHolder;
import store.ppingpong.board.user.domain.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class ForumTest {

    @Test
    void ForumCreate로_Forum을_만들_수_있다() {
        // given
        ForumCreate forumCreate = ForumCreate.builder()
                .forumId("reverse1999")
                .name("리버스1999")
                .introduction("리버스1999 입니다.")
                .category(Category.GAME)
                .build();

        LoginInfo loginInfo = LoginInfo.builder()
                .loginId("ssar1234")
                .loginType(LoginType.NAVER)
                .encodePassword("abc123def45")
                .build();
        UserInfo userInfo = UserInfo.builder()
                .nickname("쌀")
                .email("ssar@naver.com")
                .userEnum(UserEnum.USER)
                .build();

        User user = User.builder()
                .id(1L)
                .loginInfo(loginInfo)
                .userInfo(userInfo)
                .userStatus(UserStatus.ACTIVE)
                .build();

        Forum forum = Forum.of(forumCreate, new TestClockLocalHolder(LocalDateTime.MIN), user.getUserInfo().getUserEnum());

        assertThat(forum.getForumId()).isEqualTo("reverse1999");
        assertThat(forum.getName()).isEqualTo("리버스1999");
        assertThat(forum.getIntroduction()).isEqualTo("리버스1999 입니다.");
        assertThat(forum.getCategory()).isEqualTo(Category.GAME);
        assertThat(forum.getForumStatus()).isEqualTo(ForumStatus.PENDING);
        assertThat(forum.getCreatedAt()).isEqualTo(LocalDateTime.MIN);
    }

    @Test
    void ForumUpdate로_Forum을_수정할_수_있다() {
        Forum forum = Forum.builder()
                .forumId("reverse1999")
                .name("리버스1999")
                .introduction("기존 소개글")
                .category(Category.ADULT)
                .forumStatus(ForumStatus.ACTIVE)
                .createdAt(LocalDateTime.MIN)
                .lastModifiedAt(null)
                .build();
        ForumUpdate forumUpdate = ForumUpdate.builder()
                .introduction("수정된 소개글")
                .category(Category.GAME)
                .build();
        forum = forum.managerModify(forumUpdate, new TestClockLocalHolder(LocalDateTime.MAX));

        assertThat(forum.getForumId()).isEqualTo("reverse1999");
        assertThat(forum.getName()).isEqualTo("리버스1999");
        assertThat(forum.getIntroduction()).isEqualTo("수정된 소개글");
        assertThat(forum.getCategory()).isEqualTo(Category.GAME);
        assertThat(forum.getForumStatus()).isEqualTo(ForumStatus.ACTIVE);
        assertThat(forum.getCreatedAt()).isEqualTo(LocalDateTime.MIN);
        assertThat(forum.getLastModifiedAt()).isEqualTo(LocalDateTime.MAX);
    }
}
