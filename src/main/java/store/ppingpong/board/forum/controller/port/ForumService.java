package store.ppingpong.board.forum.controller.port;

import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.dto.ForumCreate;
import store.ppingpong.board.forum.dto.ForumUpdate;
import store.ppingpong.board.user.domain.User;

import java.util.List;

public interface ForumService {
    Forum create(ForumCreate forumCreate, User user);
    List<Forum> getActiveList();
    Forum findById(String forumId);
    Forum modify(String forumId, ForumUpdate forumUpdate);





}
