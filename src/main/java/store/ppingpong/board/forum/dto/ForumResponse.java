package store.ppingpong.board.forum.dto;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.forum.domain.Category;
import store.ppingpong.board.forum.domain.Forum;

@Getter
@Builder
public class ForumResponse {

    private long id;
    private String forumId;
    private String name;
    private Category category;
    private long createdAt;

    public static ForumResponse from(Forum forum) {
        return ForumResponse.builder()
                .id(forum.getId())
                .forumId(forum.getForumId())
                .name(forum.getName())
                .category(forum.getCategory())
                .createdAt(forum.getCreatedAt())
                .build();
    }
}
