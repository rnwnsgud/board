package store.ppingpong.board.mock;

import store.ppingpong.board.user.service.port.EmailSender;

public class FakeEmailSender implements EmailSender {

    public String email;
    public String title;
    public String content;

    @Override
    public void send(String email, String title, String content) {
        this.email = email;
        this.title = title;
        this.content = content;
    }
}
