package store.ppingpong.board.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.common.util.ValidEnum;
import store.ppingpong.board.post.domain.PostType;

@Getter
public class PostCreate {
    private final String title;
    private final String content;
    @ValidEnum(enumClass = PostType.class)
    private final PostType postType;

    @Builder
    public PostCreate(@JsonProperty("title") String title,
                      @JsonProperty("content") String content,
                      @JsonProperty("postType") PostType postType) {
        this.title = title;
        this.content = content;
        this.postType = postType;
    }
}
