package store.ppingpong.board.forum.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.forum.domain.ForumUser;
import store.ppingpong.board.forum.service.port.ForumUserRepository;

@Repository
@RequiredArgsConstructor
public class ForumUserRepositoryImpl implements ForumUserRepository {

    private final ForumUserJpaRepository forumUserJpaRepository;
    @Override
    public ForumUser save(ForumUser forumUser) {
        return forumUserJpaRepository.save(ForumUserEntity.from(forumUser)).toModel();
    }
}
