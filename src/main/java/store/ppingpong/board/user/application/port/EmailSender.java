package store.ppingpong.board.user.application.port;

public interface EmailSender {

    void send(String email, String title, String content);
}
