package store.ppingpong.board.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreateRequest {
    private final String title;
    private final String content;
    private final Long postTypeId;

    @Builder
    public PostCreateRequest(@JsonProperty("title") String title,
                             @JsonProperty("content") String content,
                             @JsonProperty("postType") Long postTypeId) {
        this.title = title;
        this.content = content;
        this.postTypeId = postTypeId;
    }
}
