package store.ppingpong.board.common.handler.exception;

public class FileUploadException extends IllegalArgumentException {
    public FileUploadException() {
        super("MultipartFile -> File 전환 실패");
    }
}
