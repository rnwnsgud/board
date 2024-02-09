package store.ppingpong.board.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.ppingpong.board.common.handler.exception.ResourceAlreadyExistException;
import store.ppingpong.board.mock.TestClockHolder;
import store.ppingpong.board.mock.user.*;
import store.ppingpong.board.user.domain.*;
import store.ppingpong.board.user.dto.UserCreate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class UserServiceTest {

    private UserServiceImpl userService;

    @BeforeEach
    void init() {

        FakeEmailSender fakeEmailSender = new FakeEmailSender();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        FakePasswordEncoder fakePasswordEncoder = new FakePasswordEncoder();
        userService = UserServiceImpl.builder()
                .inMemoryService(new FakeRedisService())
                .certificationService(new CertificationService(fakeEmailSender))
                .userRepository(fakeUserRepository)
                .passwordEncoder(fakePasswordEncoder)
                .randomHolder(() -> "123456")
                .clockHolder(new TestClockHolder(100L))
                .build();

        UserInfo userInfo = UserInfo.builder()
                .userEnum(UserEnum.USER)
                .email("ssar@naver.com")
                .nickname("쌀")
                .build();

        LoginInfo loginInfo = LoginInfo.builder()
                .loginId("ssar1234")
                .encodePassword("1234ABCD56")
                .loginType(LoginType.NAVER)
                .build();

        fakeUserRepository.save(User.builder()
                .id(1L)
                .userStatus(UserStatus.PENDING)
                .createdAt(100L)
                .userInfo(userInfo)
                .loginInfo(loginInfo)
                .build());

    }

    @Test
    void UserCreate_객체로_PENDING_상태인_유저를_생성할_수_있다() {

        UserCreate userCreate = UserCreate
                .builder()
                .loginId("cos1234")
                .rawPassword("1234")
                .nickname("쌀루스")
                .email("ssar@gmail.com").build();

        User user = userService.create(userCreate);
        assertThat(user.getUserStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getUserInfo().getUserEnum()).isEqualTo(UserEnum.USER);
        assertThat(user.getUserInfo().getNickname()).isEqualTo("쌀루스");
        assertThat(user.getUserInfo().getEmail()).isEqualTo("ssar@gmail.com");
        assertThat(user.getLoginInfo().getLoginId()).isEqualTo("cos1234");
        assertThat(user.getLoginInfo().getLoginType()).isEqualTo(LoginType.GOOGLE);
        assertThat(user.getLoginInfo().getEncodePassword()).isEqualTo("1234");
        assertThat(user.getLastLoginAt()).isNull();

    }

    @Test
    void create는_기존_유저가_존재하면_예외를_반환한다() {

        UserCreate userCreate = UserCreate
                .builder()
                .loginId("ssar1234")
                .rawPassword("1234")
                .nickname("쌀루스")
                .email("ssar@google.com").build();

        assertThatThrownBy(() -> {
            userService.create(userCreate);
        }).isInstanceOf(ResourceAlreadyExistException.class);

    }


}
