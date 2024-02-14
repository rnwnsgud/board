package store.ppingpong.board.forum.dto;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.forum.domain.Category;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.ForumStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class ForumResponse {

    private String forumId;
    private String name;
    private Category category;
    private ForumStatus forumStatus;
    private LocalDateTime createdAt;

    public static ForumResponse from(Forum forum) {
        return ForumResponse.builder()
                .forumId(forum.getForumId())
                .name(forum.getName())
                .category(forum.getCategory())
                .forumStatus(forum.getForumStatus())
                .createdAt(forum.getCreatedAt())
                .build();
    }
}
