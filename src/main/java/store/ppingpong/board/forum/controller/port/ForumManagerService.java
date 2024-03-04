package store.ppingpong.board.forum.controller.port;

import store.ppingpong.board.forum.domain.ForumManager;

public interface ForumManagerService {

    ForumManager findForumManager(String forumId);
    ForumManager save(ForumManager forumManager);

}
