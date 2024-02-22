package store.ppingpong.board.forum.service.port;

import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.domain.ForumManagerLevel;

import java.util.List;

public interface ForumManagerRepository {

    ForumManager save(ForumManager forumManager);
    List<ForumManager> getListByForumId(String forumId);
    ForumManager getManagerByForumId(String forumId);
}
