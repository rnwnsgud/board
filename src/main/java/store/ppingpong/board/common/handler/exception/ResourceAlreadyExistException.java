package store.ppingpong.board.common.handler.exception;

public class ResourceAlreadyExistException extends RuntimeException {

    public ResourceAlreadyExistException(String message, long id) {
        super(message + " id : " + id);
    }
}
