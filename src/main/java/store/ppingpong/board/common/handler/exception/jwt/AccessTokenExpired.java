package store.ppingpong.board.common.handler.exception.jwt;

public class AccessTokenExpired extends RuntimeException {

    public AccessTokenExpired() {
        super("Access Token이 만료되었습니다.");
    }
}
