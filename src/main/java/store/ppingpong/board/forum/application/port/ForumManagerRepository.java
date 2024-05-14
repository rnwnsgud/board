package store.ppingpong.board.forum.application.port;

import store.ppingpong.board.forum.domain.ForumManager;

public interface ForumManagerRepository {
    ForumManager save(ForumManager forumManager);
    ForumManager findManagerByForumId(String forumId);
    void deleteByUserId(String forumId, Long userId);
    ForumManager findForumUserOrCreate(String forumId, Long userId);
}
