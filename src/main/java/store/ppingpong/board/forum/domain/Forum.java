package store.ppingpong.board.forum.domain;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.common.service.port.ClockHolder;
import store.ppingpong.board.forum.dto.ForumCreate;

@Getter
public class Forum {
    private final Long id;
    private final String forumId;
    private final String name;
    private final String introduction;
    private final Category category;
    private final Long createdAt;


    @Builder
    private Forum(Long id, String forumId, String name, String introduction, Category category, Long createdAt) {
        this.id = id;
        this.forumId = forumId;
        this.name = name;
        this.introduction = introduction;
        this.category = category;
        this.createdAt = createdAt;
    }

    public static Forum from(ForumCreate forumCreate, ClockHolder clockHolder) {
        return Forum.builder()
                .forumId(forumCreate.getForumId())
                .name(forumCreate.getName())
                .introduction(forumCreate.getIntroduction())
                .category(forumCreate.getCategory())
                .createdAt(clockHolder.mills())
                .build();
    }
}
