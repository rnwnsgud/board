package store.ppingpong.board.forum.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.domain.ForumManagerLevel;
import store.ppingpong.board.forum.application.port.ForumManagerRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ForumManagerRepositoryImpl implements ForumManagerRepository {

    private final ForumManagerJpaRepository forumManagerJpaRepository;

    @Override
    public ForumManager save(ForumManager forumManager) {
        return forumManagerJpaRepository.save(ForumManagerEntity.from(forumManager)).toModel();

    }
    @Override
    public ForumManager findManagerByForumId(String forumId) {
        return forumManagerJpaRepository.findMangerByForumId(forumId).toModel();
    }

    @Override
    public void deleteByUserId(String forumId, Long userId) {
        forumManagerJpaRepository.deleteByForumIdAndUserId(forumId, userId);
    }
    @Override
    public ForumManager findForumUserOrCreate(String forumId, Long userId) {
        Optional<ForumManagerEntity> forumManagerOptional = forumManagerJpaRepository.findByForumIdAndUserId(forumId, userId);
        if (forumManagerOptional.isEmpty()) {
            return save(ForumManager.of(forumId, userId, ForumManagerLevel.USER));
        } else return forumManagerOptional.get().toModel();
    }


}
