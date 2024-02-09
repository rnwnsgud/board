package store.ppingpong.board.forum.service.port;

import store.ppingpong.board.forum.domain.ForumUser;

public interface ForumUserRepository {

    ForumUser save(ForumUser forumUser);
}
