package store.ppingpong.board.forum.domain;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.common.service.port.ClockHolder;
import store.ppingpong.board.forum.dto.ForumCreate;
import store.ppingpong.board.user.domain.UserEnum;

import java.time.Clock;
import java.time.LocalDateTime;

@Getter
public class Forum {

    private final String forumId;
    private final String name;
    private final String introduction;
    private final Category category;
    private final ForumStatus forumStatus;
    private final LocalDateTime createdAt;


    @Builder
    private Forum(String forumId, String name, String introduction, Category category, ForumStatus forumStatus, LocalDateTime createdAt) {

        this.forumId = forumId;
        this.name = name;
        this.introduction = introduction;
        this.category = category;
        this.forumStatus = forumStatus;
        this.createdAt = createdAt;
    }

    public static Forum valueOf(ForumCreate forumCreate, ClockHolder clockHolder, UserEnum userEnum) {
        ForumStatus forumStatus;
        if (userEnum == UserEnum.USER) forumStatus = ForumStatus.PENDING;
        else forumStatus = ForumStatus.ACTIVE;
        return Forum.builder()
                .forumId(forumCreate.getForumId())
                .name(forumCreate.getName())
                .introduction(forumCreate.getIntroduction())
                .category(forumCreate.getCategory())
                .forumStatus(forumStatus)
                .createdAt(clockHolder.localMills())
                .build();
    }
}
