package store.ppingpong.board.common.handler.exception.resource;

public class ResourceNotSameException extends RuntimeException {
    public ResourceNotSameException(String datasource, long id) {
        super(datasource +"에서 ID "+ id + "는 로그인 한 사용자가 아닙니다.");
    }
}

