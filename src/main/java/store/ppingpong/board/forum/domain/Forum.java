package store.ppingpong.board.forum.domain;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.common.service.port.ClockLocalHolder;
import store.ppingpong.board.forum.dto.ForumCreate;
import store.ppingpong.board.forum.dto.ForumUpdate;
import store.ppingpong.board.forum.infrastructure.ForumEntity;
import store.ppingpong.board.user.domain.UserEnum;

import java.time.LocalDateTime;

@Getter
public class Forum {

    private final String forumId;
    private final String name;
    private final String introduction;
    private final Category category;
    private final ForumStatus forumStatus;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastModifiedAt;

    @Builder
    private Forum(String forumId, String name, String introduction, Category category, ForumStatus forumStatus, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.forumId = forumId;
        this.name = name;
        this.introduction = introduction;
        this.category = category;
        this.forumStatus = forumStatus;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static Forum of(ForumCreate forumCreate, ClockLocalHolder clockLocalHolder, UserEnum userEnum) {
        ForumStatus forumStatus;
        if (userEnum == UserEnum.USER) forumStatus = ForumStatus.PENDING;
        else forumStatus = ForumStatus.ACTIVE;
        return Forum.builder()
                .forumId(forumCreate.getForumId())
                .name(forumCreate.getName())
                .introduction(forumCreate.getIntroduction())
                .category(forumCreate.getCategory())
                .forumStatus(forumStatus)
                .createdAt(clockLocalHolder.localMills())
                .build();
    }

    public static Forum from(ForumEntity forumEntity) {
        return Forum.builder()
                .forumId(forumEntity.getForumId())
                .name(forumEntity.getName())
                .introduction(forumEntity.getIntroduction())
                .category(forumEntity.getCategory())
                .forumStatus(forumEntity.getForumStatus())
                .createdAt(forumEntity.getCreatedAt())
                .build();
    }

    public Forum managerModify(ForumUpdate forumUpdate, ClockLocalHolder clockLocalHolder) {
        return Forum.builder()
                .forumId(forumId)
                .name(name)
                .introduction(forumUpdate.getIntroduction())
                .category(category)
                .forumStatus(forumStatus)
                .createdAt(createdAt)
                .lastModifiedAt(clockLocalHolder.localMills())
                .build();
    }
}
