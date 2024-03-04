package store.ppingpong.board.forum.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.service.port.ForumManagerRepository;

@Repository
@RequiredArgsConstructor
public class ForumManagerRepositoryImpl implements ForumManagerRepository {

    private final ForumManagerJpaRepository forumManagerJpaRepository;

    @Override
    public ForumManager save(ForumManager forumManager) {
        return forumManagerJpaRepository.save(ForumManagerEntity.from(forumManager)).toModel();

    }
    @Override
    public ForumManager findByForumId(String forumId) {
        return forumManagerJpaRepository.findMangerByForumId(forumId).toModel();
    }


}
