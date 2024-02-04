package store.ppingpong.board.forum.domain;

import lombok.Builder;
import store.ppingpong.board.user.domain.User;

public class ForumUser {
    private final Long id;
    private final Forum board;
    private final User user;
    private final ForumUserLevel forumUserLevel;

    @Builder
    public ForumUser(Long id, Forum board, User user, ForumUserLevel forumUserLevel) {
        this.id = id;
        this.board = board;
        this.user = user;
        this.forumUserLevel = forumUserLevel;
    }
}
