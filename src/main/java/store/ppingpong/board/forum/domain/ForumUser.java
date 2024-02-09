package store.ppingpong.board.forum.domain;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.user.domain.User;
@Getter
public class ForumUser {
    private final Long id;

    private final Forum forum;
    private final User user;
    private final ForumUserLevel forumUserLevel;

    @Builder
    public ForumUser(Long id, Forum forum, User user, ForumUserLevel forumUserLevel) {
        this.id = id;
        this.forum = forum;
        this.user = user;
        this.forumUserLevel = forumUserLevel;
    }
}
