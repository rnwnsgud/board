package store.ppingpong.board.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDeleteResponseDto {

    private final int status;
    private final int deletedImageCount;

    @Builder
    public PostDeleteResponseDto(int status, int deletedImageCount) {
        this.status = status;
        this.deletedImageCount = deletedImageCount;
    }
}
