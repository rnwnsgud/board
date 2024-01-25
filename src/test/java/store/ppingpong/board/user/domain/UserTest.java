package store.ppingpong.board.user.domain;

import org.junit.jupiter.api.Test;
import store.ppingpong.board.mock.TestClockHolder;

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
      User user = User.of(loginInfo, userInfo, new TestClockHolder(100));

      // then
      assertThat(user.getId()).isNull();
      assertThat(user.getUserStatus()).isEqualTo(UserStatus.PENDING);
      assertThat(user.getUserInfo()).isEqualTo(userInfo);
      assertThat(user.getLoginInfo()).isEqualTo(loginInfo);
      assertThat(user.getCreatedAt()).isEqualTo(100);
      assertThat(user.getLastLoginAt()).isNull();
  }
}
