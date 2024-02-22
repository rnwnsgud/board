package store.ppingpong.board.forum.controller.port;

import store.ppingpong.board.forum.domain.ForumManager;

import java.util.List;

public interface ForumManagerService {

    List<ForumManager> findForumManagers(String forumId);
    ForumManager findForumManager(String forumId);
    ForumManager save(ForumManager forumManager);

}
