package store.ppingpong.board.forum.domain;

import lombok.*;
import store.ppingpong.board.common.handler.exception.ResourceNotSameException;
import store.ppingpong.board.forum.infrastructure.ForumManagerEntity;
import store.ppingpong.board.user.domain.User;

import java.util.Objects;

@Getter
public class ForumManager {
    private final Long id;
    private final String forumId;
    private final Long userId;
    private final ForumManagerLevel forumManagerLevel;

    @Builder(access = AccessLevel.PRIVATE)
    private ForumManager(Long id, String forumId, Long userId, ForumManagerLevel forumManagerLevel) {
        this.id = id;
        this.forumId = forumId;
        this.userId = userId;
        this.forumManagerLevel = forumManagerLevel;
    }

    public static ForumManager of(String forumId, Long userId, ForumManagerLevel forumManagerLevel) {
        return ForumManager.builder()
                .forumId(forumId)
                .userId(userId)
                .forumManagerLevel(forumManagerLevel)
                .build();
    }

    public static ForumManager from(ForumManagerEntity forumManagerEntity) {
        return ForumManager.builder()
                .id(forumManagerEntity.getId())
                .userId(forumManagerEntity.getUserId())
                .forumId(forumManagerEntity.getForumId())
                .build();
    }


    public void isSameUser(User user) {
        if(!Objects.equals(this.userId, user.getId())) throw new ResourceNotSameException("본인은 해당 포럼의 매니저가 아닙니다. : ", user.getId());
    }
}
