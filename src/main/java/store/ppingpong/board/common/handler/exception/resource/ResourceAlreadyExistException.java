package store.ppingpong.board.common.handler.exception.resource;

public class ResourceAlreadyExistException extends RuntimeException {

    public ResourceAlreadyExistException(String message, long id) {
        super(message + " id : " + id);
    }
    public ResourceAlreadyExistException(String message, String id) {
        super(message + " id : " + id);
    }

    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}
