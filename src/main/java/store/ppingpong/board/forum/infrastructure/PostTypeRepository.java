package store.ppingpong.board.forum.infrastructure;

import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.PostType;

import java.util.List;

public interface PostTypeRepository {
    List<PostType> findByNameInAndCreate(String[] postTypeNames, Forum forum);
}
