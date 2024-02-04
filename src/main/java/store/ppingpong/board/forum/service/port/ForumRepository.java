package store.ppingpong.board.forum.service.port;

import store.ppingpong.board.forum.domain.Forum;

public interface ForumRepository {
    Forum save(Forum forum);
}
