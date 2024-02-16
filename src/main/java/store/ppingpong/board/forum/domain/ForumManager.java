package store.ppingpong.board.forum.domain;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.user.domain.User;
@Getter
public class ForumManager {
    private final Long id;

    private final Forum forum;
    private final User user;
    private final ForumManagerLevel forumUserLevel;

    @Builder
    public ForumManager(Long id, Forum forum, User user, ForumManagerLevel forumUserLevel) {
        this.id = id;
        this.forum = forum;
        this.user = user;
        this.forumUserLevel = forumUserLevel;
    }
}
