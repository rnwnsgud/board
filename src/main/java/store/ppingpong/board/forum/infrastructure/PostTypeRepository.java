package store.ppingpong.board.forum.infrastructure;

import store.ppingpong.board.forum.domain.Forum;

import java.util.List;

public interface PostTypeRepository {

    List<PostTypeEntity> findByNameInAndCreate(String[] postTypeNames, Forum forum);

    void save(PostTypeEntity postTypeEntity);
}
