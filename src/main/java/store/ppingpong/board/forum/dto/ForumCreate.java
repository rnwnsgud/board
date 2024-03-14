package store.ppingpong.board.forum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.yaml.snakeyaml.util.EnumUtils;
import store.ppingpong.board.forum.domain.Category;

@Getter
public class ForumCreate {
    @Size(min = 2, max = 12)
    private final String name;
    @Size(max = 200)
    private final String introduction;
    @Size(min = 2, max = 20)
    private final String forumId;
    private final Category category;

    @Builder
    public ForumCreate(@JsonProperty("name") String name,
                       @JsonProperty("introduction") String introduction,
                       @JsonProperty("forumId") String forumId,
                       @JsonProperty("category") Category category) {
        this.name = name;
        this.introduction = introduction;
        this.forumId = forumId;
        this.category = category;
    }
}
