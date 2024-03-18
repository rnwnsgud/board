package store.ppingpong.board.forum.service.port;

import store.ppingpong.board.forum.domain.ForumManager;

public interface ForumManagerRepository {
    ForumManager save(ForumManager forumManager);
    ForumManager findManagerByForumId(String forumId);
    void deleteByUserId(String forumId, Long userId);
}
