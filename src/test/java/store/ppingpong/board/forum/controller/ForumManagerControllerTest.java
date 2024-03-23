package store.ppingpong.board.forum.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import store.ppingpong.board.common.ResponseDto;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.domain.ForumManagerLevel;
import store.ppingpong.board.forum.dto.ForumAssistantResponse;
import store.ppingpong.board.mock.forum.TestForumManagerContainer;
import store.ppingpong.board.user.domain.*;

import static org.assertj.core.api.Assertions.*;

public class ForumManagerControllerTest {
    TestForumManagerContainer testForumManagerContainer = new TestForumManagerContainer();
    LoginUser loginUser;

    @BeforeEach
    void init() {
        UserInfo userInfo = UserInfo.builder()
                .userType(UserType.USER)
                .email("ssar@naver.com")
                .nickname("쌀")
                .build();

        LoginInfo loginInfo = LoginInfo.builder()
                .loginId("ssar1234")
                .encodePassword("1234ABCD56")
                .loginType(LoginType.NAVER)
                .build();

        User owner = User.builder()
                .id(1L)
                .loginInfo(loginInfo)
                .userInfo(userInfo)
                .build();
        loginUser = new LoginUser(owner);
        User user = User.builder()
                .id(2L)
                .loginInfo(loginInfo)
                .userInfo(userInfo)
                .build();

        testForumManagerContainer.userRepository.save(owner);
        testForumManagerContainer.userRepository.save(user);
    }

    @Test
    void Forum의_MANAGER는_ASSISTANT를_임명할_수_있다() {
        // given
        ForumManager forumManager = ForumManager.of("reverse1999", 1L, ForumManagerLevel.MANAGER);
        testForumManagerContainer.forumManagerRepository.save(forumManager);

        // when
        ResponseEntity<ResponseDto<ForumAssistantResponse>> response = testForumManagerContainer.forumManagerController.appointmentAssistant("reverse1999", 2L, loginUser);

        // then
        assertThat(response.getBody().getCode()).isEqualTo(1);
        assertThat(response.getBody().getMsg()).isEqualTo("Assistant 임명 성공");
        assertThat(response.getBody().getData().getForumId()).isEqualTo("reverse1999");
        assertThat(response.getBody().getData().getNickname()).isEqualTo("쌀");
        assertThat(response.getBody().getData().getLoginId()).isEqualTo("ssar1234");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}