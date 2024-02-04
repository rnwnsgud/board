package store.ppingpong.board.forum.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.service.port.ForumRepository;

@Repository
@RequiredArgsConstructor
public class ForumRepositoryImpl implements ForumRepository {

    private final ForumJpaRepository forumJpaRepository;

    @Override
    public Forum save(Forum forum) {
        return forumJpaRepository.save(ForumEntity.from(forum)).toModel();
    }
}
