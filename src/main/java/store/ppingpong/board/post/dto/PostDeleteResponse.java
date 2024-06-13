package store.ppingpong.board.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDeleteResponse {

    private final int status;
    private final int deletedCount;

    @Builder
    public PostDeleteResponse(int status, int deletedCount) {
        this.status = status;
        this.deletedCount = deletedCount;
    }
}
