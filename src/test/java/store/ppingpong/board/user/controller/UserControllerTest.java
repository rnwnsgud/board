package store.ppingpong.board.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import store.ppingpong.board.common.handler.exception.CertificationCodeNotMatchedException;
import store.ppingpong.board.common.handler.exception.EmailNotSupportException;
import store.ppingpong.board.common.handler.exception.ResourceAlreadyExistException;
import store.ppingpong.board.mock.user.TestClockHolder;
import store.ppingpong.board.mock.user.TestUserContainer;
import store.ppingpong.board.user.controller.response.UserResponse;
import store.ppingpong.board.user.domain.*;
import store.ppingpong.board.user.dto.UserCreate;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class UserControllerTest {

    @Test
    void 회원가입_사용자는_PENDING_상태이며_LOGINTYPE이_할당되었다() {

        // given
        TestUserContainer testContainer = TestUserContainer.builder()
                .randomHolder(() -> "123456")
                .clockHolder(new TestClockHolder(300L))
                .build();

        UserCreate userCreate = UserCreate.builder()
                .email("ssar@naver.com")
                .nickname("쌀")
                .loginId("ssar1234")
                .rawPassword("1234")
                .build();

        // when
        ResponseEntity<UserResponse> response = testContainer.userController.sendEmail(userCreate);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNickname()).isEqualTo("쌀");
        assertThat(response.getBody().getEmail()).isEqualTo("ssar@naver.com");
        assertThat(response.getBody().getCreatedAt()).isEqualTo(100L);
        assertThat(response.getBody().getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(response.getBody().getLoginType()).isEqualTo(LoginType.NAVER);

    }


    @Test
    void 회원가입_시_이메일형식이_네이버_및_구글_이메일이_아니면_오류를_발생한다() {
        // given
        TestUserContainer testContainer = TestUserContainer.builder()
                .build();

        UserCreate userCreate = UserCreate.builder()
                .email("ssar@daum.com")
                .nickname("쌀")
                .loginId("ssar1234")
                .rawPassword("1234")
                .build();

        // when
        // then
        assertThatThrownBy(() -> {
            testContainer.userController.sendEmail(userCreate);
        }).isInstanceOf(EmailNotSupportException.class);
    }

    @Test
    void 이미_회원가입된_사용자가_회원가입을_진행하면_400_응답을_받는다() {
        // given
        TestUserContainer testContainer = TestUserContainer.builder()
                .build();

        LoginInfo loginInfo = LoginInfo.builder()
                .loginId("ssar1234")
                .build();

        testContainer.userRepository.save(User.builder()
                .id(1L)
                .userInfo(null)
                .loginInfo(loginInfo)
                .userStatus(UserStatus.PENDING)
                .createdAt(100L)
                .lastLoginAt(null)
                .build()
        );

        // when
        UserCreate userCreate = UserCreate.builder()
                .email("ssar@naver.com")
                .nickname("쌀")
                .loginId("ssar1234")
                .rawPassword("1234")
                .build();

        // then
        assertThatThrownBy(() -> {
            testContainer.userController.sendEmail(userCreate);
        }).isInstanceOf(ResourceAlreadyExistException.class);

    }

    @Test
    void 사용자는_인증코드로_계정을_활성화_시킬_수_있다() {
        // given
        TestUserContainer testContainer = TestUserContainer.builder()
                .build();

        LoginInfo loginInfo = LoginInfo.builder()
                .loginId("ssar1234")
                .build();

        UserInfo userInfo = UserInfo.builder()
                .email("ssar@naver.com")
                .build();

        testContainer.userRepository.save(User.builder()
                .id(1L)
                .userInfo(userInfo)
                .loginInfo(loginInfo)
                .userStatus(UserStatus.PENDING)
                .createdAt(100L)
                .lastLoginAt(null)
                .build()
        );

        testContainer.inMemoryService.setValueExpire("ssar@naver.com","123456", 100);

        // when
        testContainer.userController.verifyEmail(1L, "123456");

        // then

        User user = testContainer.userRepository.getById(1L);
        assertThat(user.getUserStatus()).isEqualTo(UserStatus.ACTIVE);

    }

    @Test
    void 사용자는_인증_코드가_일치하지_않으면_권한_없음_에러를_내려준다() {
        // given
        TestUserContainer testContainer = TestUserContainer.builder()
                .build();

        LoginInfo loginInfo = LoginInfo.builder()
                .loginId("ssar1234")
                .build();

        UserInfo userInfo = UserInfo.builder()
                .email("ssar@naver.com")
                .build();

        testContainer.userRepository.save(User.builder()
                .id(1L)
                .userInfo(userInfo)
                .loginInfo(loginInfo)
                .userStatus(UserStatus.PENDING)
                .createdAt(100L)
                .lastLoginAt(null)
                .build()
        );

        testContainer.inMemoryService.setValueExpire("ssar@naver.com","123456", 100);

        // when

        // then
        assertThatThrownBy(() -> {
            testContainer.userController.verifyEmail(1L, "999999");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);

    }


}