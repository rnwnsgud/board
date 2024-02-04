package store.ppingpong.board.forum.controller.port;

import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.dto.ForumCreate;

public interface ForumService {
    Forum create(ForumCreate forumCreate);

}
