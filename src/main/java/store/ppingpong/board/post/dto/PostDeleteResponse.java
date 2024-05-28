package store.ppingpong.board.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDeleteResponse {

    private final int status;
    private final int deletedImageCount;

    @Builder
    public PostDeleteResponse(int status, int deletedImageCount) {
        this.status = status;
        this.deletedImageCount = deletedImageCount;
    }
}
