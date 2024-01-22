package store.ppingpong.board.user.service.port;

public interface CustomPasswordEncoder {

    String encode(String rawPassword);
}
