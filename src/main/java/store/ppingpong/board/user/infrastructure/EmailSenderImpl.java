package store.ppingpong.board.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import store.ppingpong.board.user.service.port.EmailSender;

@Component
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender javaMailSender;

    @Override
    public void send(String email, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage(); // 멀티파트 데이터는 MimeMessage
        message.setFrom("rnwnsgud90@naver.com"); // config에 설정한 본인 이메일
        message.setSubject(title);
        message.setTo(email);
        message.setText(content);
        javaMailSender.send(message);

    }


}
