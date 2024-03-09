package store.ppingpong.board.forum.dto;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.common.service.port.ClockLocalHolder;
import store.ppingpong.board.forum.domain.Category;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.user.domain.User;

import java.time.LocalDateTime;

@Getter
@Builder
public class ForumUpdateResponse {
    private String forumId;
    private String name;
    private Category category;
    private long forumModifierId;
    private String forumModifierName;
    private LocalDateTime updatedAt;
    public static ForumUpdateResponse of(Forum forum, User user, ClockLocalHolder clockLocalHolder) {
        return ForumUpdateResponse.builder()
                .forumId(forum.getForumId())
                .name(forum.getName())
                .category(forum.getCategory())
                .forumModifierId(user.getId())
                .forumModifierName(user.getUserInfo().getNickname())
                .updatedAt(clockLocalHolder.localMills())
                .build();
    }
}
