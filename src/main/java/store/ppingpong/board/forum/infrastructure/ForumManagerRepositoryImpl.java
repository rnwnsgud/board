package store.ppingpong.board.forum.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.domain.ForumManagerLevel;
import store.ppingpong.board.forum.service.port.ForumManagerRepository;


import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ForumManagerRepositoryImpl implements ForumManagerRepository {

    private final ForumManagerJpaRepository forumManagerJpaRepository;

    @Override
    public ForumManager save(ForumManager forumManager) {
        return forumManagerJpaRepository.save(ForumManagerEntity.from(forumManager)).toModel();
    }

    @Override
    public List<ForumManager> getListByForumId(String forumId) {
        return forumManagerJpaRepository.getListByForumId(forumId).stream().map(ForumManagerEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public ForumManager getManagerByForumId(String forumId) {
        return forumManagerJpaRepository.getByForumIdAndLevel(forumId).toModel();
    }


}
