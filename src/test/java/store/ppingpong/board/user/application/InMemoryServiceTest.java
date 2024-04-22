package store.ppingpong.board.user.application;

import org.junit.jupiter.api.Test;
import store.ppingpong.board.common.domain.InMemoryService;
import store.ppingpong.board.mock.user.FakeRedisService;

import static org.assertj.core.api.Assertions.*;

public class InMemoryServiceTest {

    private final InMemoryService inMemoryService = new FakeRedisService();

    @Test
    void 지정한_기간동안의_데이터가_저장된다() {
        // given
        String email = "ssar@naver.com";
        String certificationCode = "1234";
        long duration = 100L;

        // when
        inMemoryService.setValueExpire(email, certificationCode, duration);

        // then
        assertThat(inMemoryService.getValue(email)).isEqualTo("1234");
        assertThat(inMemoryService.getExpirationTime(email)).isEqualTo(100L);
    }

    @Test
    void 인증코드를_검증할_수_있다() {
        // given
        String email = "ssar@naver.com";
        String certificationCode = "1234";
        long duration = 100L;
        inMemoryService.setValueExpire(email, certificationCode, duration);

        // when
        inMemoryService.verifyCode(email, certificationCode);

    }

    @Test
    void 잘못된_인증코드는_예외를_던진다() {
        // given
        String email = "ssar@naver.com";
        String certificationCode = "1234";
        long duration = 100L;
        inMemoryService.setValueExpire(email, certificationCode, duration);

        // when
        assertThatThrownBy(() -> inMemoryService.verifyCode(email, "12345"));       // then

    }
}
