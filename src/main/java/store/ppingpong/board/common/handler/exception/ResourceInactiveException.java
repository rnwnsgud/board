package store.ppingpong.board.common.handler.exception;

public class ResourceInactiveException extends RuntimeException{
    public ResourceInactiveException(String message, long id) {
        super(message + " id : " + id);
    }
}
