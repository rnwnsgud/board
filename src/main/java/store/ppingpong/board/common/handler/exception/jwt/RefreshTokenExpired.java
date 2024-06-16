package store.ppingpong.board.common.handler.exception.jwt;

public class RefreshTokenExpired extends RuntimeException {
    public RefreshTokenExpired() {
        super("RefreshToken이 만료되었습니다. 다시 로그인 해주세요.");
    }
}
