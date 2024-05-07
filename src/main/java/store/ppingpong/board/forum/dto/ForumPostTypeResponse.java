package store.ppingpong.board.forum.dto;

import lombok.Getter;
import store.ppingpong.board.forum.infrastructure.PostTypeEntity;

import java.util.List;

@Getter
public class ForumPostTypeResponse {

    private final List<String> forumPostTypes;

    public ForumPostTypeResponse(List<String> forumPostTypes) {
        this.forumPostTypes = forumPostTypes;
    }



}
