package store.ppingpong.board.forum.domain;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.forum.infrastructure.PostTypeEntity;

@Getter
public class PostType {
    private final Long id;
    private final String name;

    @Builder
    private PostType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static PostType from(PostTypeEntity postTypeEntity) {
        return PostType.builder()
                .id(postTypeEntity.getId())
                .name(postTypeEntity.getName())
                .build();
    }
}
