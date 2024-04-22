package store.ppingpong.board.user.application.port;

public interface CustomPasswordEncoder {

    String encode(String rawPassword);
}
