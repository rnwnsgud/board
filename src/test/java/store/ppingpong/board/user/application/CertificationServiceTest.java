package store.ppingpong.board.user.application;

import org.junit.jupiter.api.Test;
import store.ppingpong.board.mock.user.FakeEmailSender;

import static org.assertj.core.api.Assertions.*;

public class CertificationServiceTest {

    @Test
    void 이메일과_컨텐츠가_제대로_만들어진다() {
        // given
        FakeEmailSender fakeEmailSender = new FakeEmailSender();
        CertificationService certificationService = new CertificationService(fakeEmailSender);

        // when
        certificationService.send("ssar@naver.com", 1L, "213561");

        // then
        assertThat(fakeEmailSender.email).isEqualTo("ssar@naver.com");
        assertThat(fakeEmailSender.title).isEqualTo("본인인증 확인 이메일입니다.");
        assertThat(fakeEmailSender.content).isEqualTo("다음 링크를 눌러서 본인의 이메일을 인증하세요: http://localhost:8080/api/users/1/verify?certificationCode=213561");
    }
}
