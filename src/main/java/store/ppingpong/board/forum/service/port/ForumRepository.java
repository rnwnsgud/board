package store.ppingpong.board.forum.service.port;

import store.ppingpong.board.forum.domain.Forum;

import java.util.List;

public interface ForumRepository {
    Forum save(Forum forum);

    List<Forum> getActiveList();
}
