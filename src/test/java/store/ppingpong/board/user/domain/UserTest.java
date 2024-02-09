package store.ppingpong.board.user.domain;

import org.junit.jupiter.api.Test;
import store.ppingpong.board.mock.user.TestClockHolder;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class UserTest {

  @Test
  void 임베디드_객체로_생성할_수_있다() {
      // given
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

      // when
      User user = User.of(loginInfo, userInfo, new TestClockHolder(100, LocalDateTime.MIN));

      // then
      assertThat(user.getId()).isNull();
      assertThat(user.getUserStatus()).isEqualTo(UserStatus.PENDING);
      assertThat(user.getUserInfo()).isEqualTo(userInfo);
      assertThat(user.getLoginInfo()).isEqualTo(loginInfo);
      assertThat(user.getCreatedAt()).isEqualTo(100);
      assertThat(user.getLastLoginAt()).isNull();
  }

  @Test
  void 로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다() {
      // given
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
              .userStatus(UserStatus.ACTIVE)
              .loginInfo(loginInfo)
              .userInfo(userInfo)
              .createdAt(100L)
              .lastLoginAt(200L)
              .build();

      // when
      user = user.login(new TestClockHolder(123455667L, LocalDateTime.MIN));

      // then
      assertThat(user.getLastLoginAt()).isEqualTo(123455667L);

  }
}
