package store.ppingpong.board.common.handler.exception;

public class FileNotSupportedException extends IllegalArgumentException {
    public FileNotSupportedException() {
        super("지원하지않는 파일형식입니다");
    }
}
