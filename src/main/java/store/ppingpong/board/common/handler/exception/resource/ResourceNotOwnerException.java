package store.ppingpong.board.common.handler.exception.resource;

public class ResourceNotOwnerException extends RuntimeException{
    public ResourceNotOwnerException(String datasource, long userId) {
        super("유저 ID : " + userId +"는 해당 " + datasource + "소유자가 아닙니다.");
    }
}
