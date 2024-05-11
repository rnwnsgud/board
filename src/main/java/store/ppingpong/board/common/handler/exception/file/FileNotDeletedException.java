package store.ppingpong.board.common.handler.exception.file;

public class FileNotDeletedException extends RuntimeException {
    public FileNotDeletedException() {
        super("파일이 삭제되지 않았습니다.");
    }
}
