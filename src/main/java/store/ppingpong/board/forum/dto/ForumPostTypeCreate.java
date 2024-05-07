package store.ppingpong.board.forum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class ForumPostTypeCreate {

    @Size(min = 1, max = 10)
    private final String[] postTypes;

    public ForumPostTypeCreate(@JsonProperty("postTypes") String[] postTypes) {
        this.postTypes = Arrays.copyOf(postTypes, postTypes.length);
    }
}
