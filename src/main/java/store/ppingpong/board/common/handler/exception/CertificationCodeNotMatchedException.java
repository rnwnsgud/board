package store.ppingpong.board.common.handler.exception;

public class CertificationCodeNotMatchedException extends RuntimeException{

    public CertificationCodeNotMatchedException() {
        super("인증코드가 올바르지 않습니다.");
    }
}
