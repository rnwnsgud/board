package store.ppingpong.board.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.ppingpong.board.user.application.port.EmailSender;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final EmailSender emailSender;

    public void send(String email, Long userId, String certificationCode) {
        String certificationUrl = generateCertificationUrl(userId, certificationCode);
        emailSender.send(email, "본인인증 확인 이메일입니다.", "다음 링크를 눌러서 본인의 이메일을 인증하세요: " + certificationUrl);
    }

    private String generateCertificationUrl(Long userId, String certificationCode) {
        return "http://localhost:8080/api/users/" + userId + "?certificationCode=" + certificationCode;
    }
}
