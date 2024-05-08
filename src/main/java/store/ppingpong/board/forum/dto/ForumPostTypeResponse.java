package store.ppingpong.board.forum.dto;

import lombok.Getter;
import store.ppingpong.board.forum.infrastructure.PostTypeEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ForumPostTypeResponse {

    private List<String> forumPostTypesName = new ArrayList<>();

    public ForumPostTypeResponse(List<PostTypeEntity> postTypeEntities) {
        for (PostTypeEntity postTypeEntity : postTypeEntities) {
            forumPostTypesName.add(postTypeEntity.getName());
        }
    }



}
