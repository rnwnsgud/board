package store.ppingpong.board.forum.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import store.ppingpong.board.common.ResponseDto;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.forum.domain.Category;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.ForumStatus;
import store.ppingpong.board.forum.dto.ForumCreate;
import store.ppingpong.board.forum.dto.ForumListResponse;
import store.ppingpong.board.forum.dto.ForumResponse;
import store.ppingpong.board.mock.forum.TestForumContainer;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.domain.UserEnum;
import store.ppingpong.board.user.domain.UserInfo;
import store.ppingpong.board.user.domain.UserStatus;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class ForumControllerTest {

    @Test
    void 일반_사용자는_포럼생성을_할_수_있고_포럼은_PENDING_상태이다() {
        // given
        TestForumContainer testContainer = TestForumContainer.builder()
                .clockLocalHolder(() -> LocalDateTime.MIN)
                .build();
        ForumCreate forumCreate = ForumCreate.builder()
                .forumId("reverse1999")
                .name("리버스1999")
                .introduction("리버스1999 입니다.")
                .category(Category.GAME)
                .build();
        UserInfo userInfo = UserInfo.builder()
                .userEnum(UserEnum.USER)
                .email("cos@google.com")
                .nickname("코스")
                .build();
        User user = User.builder()
                .id(1L)
                .userStatus(UserStatus.ACTIVE)
                .userInfo(userInfo)
                .loginInfo(null)
                .createdAt(100L).build();
        // when
        ResponseEntity<ResponseDto<ForumResponse>> response = testContainer.forumController.create(forumCreate, null, new LoginUser(user));

        // then
        assertThat(response.getBody().getData().getForumId()).isEqualTo("reverse1999");
        assertThat(response.getBody().getData().getName()).isEqualTo("리버스1999");
        assertThat(response.getBody().getData().getCategory()).isEqualTo(Category.GAME);
        assertThat(response.getBody().getData().getCreatedAt()).isEqualTo(LocalDateTime.MIN);
        assertThat(response.getBody().getData().getForumStatus()).isEqualTo(ForumStatus.PENDING);


    }

    @Test
    void 운영자는_포럼생성을_할_수_있고_포럼은_ACTIVE_상태이다() {
        // given
        TestForumContainer testContainer = TestForumContainer.builder()
                .clockLocalHolder(() -> LocalDateTime.MIN)
                .build();
        ForumCreate forumCreate = ForumCreate.builder()
                .forumId("reverse1999")
                .name("리버스1999")
                .introduction("리버스1999 입니다.")
                .category(Category.GAME)
                .build();
        UserInfo userInfo = UserInfo.builder()
                .userEnum(UserEnum.ADMIN)
                .email("cos@google.com")
                .nickname("코스")
                .build();
        User user = User.builder()
                .id(1L)
                .userStatus(UserStatus.ACTIVE)
                .userInfo(userInfo)
                .loginInfo(null)
                .createdAt(100L).build();

        // when
        ResponseEntity<ResponseDto<ForumResponse>> response = testContainer.forumController.create(forumCreate, null, new LoginUser(user));

        // then
        assertThat(response.getBody().getData().getForumId()).isEqualTo("reverse1999");
        assertThat(response.getBody().getData().getName()).isEqualTo("리버스1999");
        assertThat(response.getBody().getData().getCategory()).isEqualTo(Category.GAME);
        assertThat(response.getBody().getData().getCreatedAt()).isEqualTo(LocalDateTime.MIN);
        assertThat(response.getBody().getData().getForumStatus()).isEqualTo(ForumStatus.ACTIVE);


    }

    @Test
    void 사용자는_ACTIVE_상태인_포럼들만_가져올_수_있다() {
        // given
        TestForumContainer testContainer = TestForumContainer.builder()
                .clockLocalHolder(() -> LocalDateTime.MIN)
                .build();
        testContainer.forumRepository.create(Forum.builder()
                .forumId("PENDING")
                .forumStatus(ForumStatus.PENDING)
                .build()
        );
        testContainer.forumRepository.create(Forum.builder()
                .forumId("ACTIVE1")
                .forumStatus(ForumStatus.ACTIVE)
                .build()
        );
        testContainer.forumRepository.create(Forum.builder()
                .forumId("ACTIVE2")
                .forumStatus(ForumStatus.ACTIVE)
                .build()
        );

        // when
        ResponseEntity<ResponseDto<ForumListResponse>> response = testContainer.forumController.getActiveList();
        // then
        assertThat(response.getBody().getData().getForumDtoList().size()).isEqualTo(2);
        assertThat(response.getBody().getData().getForumDtoList().get(0).getForumId()).startsWith("ACTIVE");


    }

}
