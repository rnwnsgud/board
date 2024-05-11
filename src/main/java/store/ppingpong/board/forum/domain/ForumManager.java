package store.ppingpong.board.forum.domain;

import lombok.*;
import store.ppingpong.board.common.handler.exception.resource.ResourceInactiveException;
import store.ppingpong.board.common.handler.exception.resource.ResourceNotSameException;
import store.ppingpong.board.forum.infrastructure.ForumManagerEntity;
import store.ppingpong.board.user.domain.User;

import java.util.Objects;

@Getter
public class ForumManager {
    private final Long id;
    private final String forumId;
    private final Long userId;
    private final ForumManagerLevel forumManagerLevel;
    private final ForumAccessStatus forumAccessStatus;

    @Builder
    private ForumManager(Long id, String forumId, Long userId, ForumManagerLevel forumManagerLevel, ForumAccessStatus forumAccessStatus) {
        this.id = id;
        this.forumId = forumId;
        this.userId = userId;
        this.forumManagerLevel = forumManagerLevel;
        this.forumAccessStatus = forumAccessStatus;
    }

    public static ForumManager of(String forumId, Long userId, ForumManagerLevel forumManagerLevel) {
        return ForumManager.builder()
                .forumId(forumId)
                .userId(userId)
                .forumManagerLevel(forumManagerLevel)
                .forumAccessStatus(ForumAccessStatus.ACTIVE)
                .build();
    }

    public static ForumManager from(ForumManagerEntity forumManagerEntity) {
        return ForumManager.builder()
                .id(forumManagerEntity.getId())
                .userId(forumManagerEntity.getUserId())
                .forumId(forumManagerEntity.getForumId())
                .forumAccessStatus(forumManagerEntity.getForumAccessStatus())
                .build();
    }

    public void isOwnerOfForum(User user) {
        if(!Objects.equals(this.userId, user.getId())) throw new ResourceNotSameException("본인은 해당 포럼의 매니저가 아닙니다. UserId : ", userId);
    }

    public void isAccessible() {
        if(forumAccessStatus != ForumAccessStatus.ACTIVE) throw new ResourceInactiveException("해당 유저는 게시글을 생성할 권한이 없습니다.", userId);
    }


}
