package store.ppingpong.board.forum.controller.port;

import store.ppingpong.board.forum.domain.ForumManager;

public interface ForumManagerService {

    ForumManager findByForumId(String forumId);
    ForumManager save(ForumManager forumManager);
    void delete(String forumId, long userId);

}
