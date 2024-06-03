package store.ppingpong.board.forum.application.port;

import store.ppingpong.board.forum.domain.Forum;

import java.util.List;
import java.util.Optional;

public interface ForumRepository {
    Forum create(Forum forum);
    List<Forum> getActiveList();
    Forum getById(String forumId);
    boolean existsById(String forumId);
    void modify(Forum forum);
}
