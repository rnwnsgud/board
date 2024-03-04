package store.ppingpong.board.forum.service.port;

import store.ppingpong.board.forum.domain.ForumManager;

public interface ForumManagerRepository {
    ForumManager save(ForumManager forumManager);
    ForumManager getManagerByForumId(String forumId);
}
