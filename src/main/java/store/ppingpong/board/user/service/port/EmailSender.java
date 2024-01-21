package store.ppingpong.board.user.service.port;

public interface EmailSender {

    void send(String email, String title, String content);
}
