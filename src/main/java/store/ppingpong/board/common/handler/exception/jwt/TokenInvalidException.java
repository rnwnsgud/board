package store.ppingpong.board.common.handler.exception.jwt;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException(String message) {
        super(message);
    }
}
