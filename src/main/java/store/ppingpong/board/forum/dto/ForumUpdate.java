package store.ppingpong.board.forum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.common.util.ValidEnum;
import store.ppingpong.board.forum.domain.Category;

@Getter
public class ForumUpdate {
    @Size(max = 200)
    private final String introduction;
    @ValidEnum(enumClass = Category.class)
    private final Category category;

    @Builder
    public ForumUpdate(@JsonProperty("introduction") String introduction,
                       @JsonProperty("category") Category category) {
        this.introduction = introduction;
        this.category = category;
    }
}
