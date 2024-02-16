package store.ppingpong.board.forum.service.port;

import store.ppingpong.board.forum.domain.ForumManager;

import java.util.List;

public interface ForumManagerRepository {

    ForumManager save(ForumManager forumUser);
    List<ForumManager> getForumManagers(String forumId);
}
