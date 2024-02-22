package store.ppingpong.board.forum.domain;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.common.handler.exception.ResourceNotSameException;
import store.ppingpong.board.user.domain.User;

import java.util.Objects;

@Getter
public class ForumManager {
    private final Long id;

    private final Forum forum;
    private final User user;
    private final ForumManagerLevel forumUserLevel;

    @Builder
    private ForumManager(Long id, Forum forum, User user, ForumManagerLevel forumUserLevel) {
        this.id = id;
        this.forum = forum;
        this.user = user;
        this.forumUserLevel = forumUserLevel;
    }

    public static ForumManager valueOf(Forum forum, User user) {
        return ForumManager.builder()
                .forum(forum)
                .user(user)
                .forumUserLevel(ForumManagerLevel.ASSISTANT)
                .build();
    }

    public void isSameUser(User user) {
        if(!Objects.equals(this.user.getId(), user.getId())) throw new ResourceNotSameException("USER", user.getId());
    }
}
