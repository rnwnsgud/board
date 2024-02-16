package store.ppingpong.board.forum.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.service.port.ForumManagerRepository;


import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ForumManagerRepositoryImpl implements ForumManagerRepository {

    private final ForumManagerJpaRepository forumUserJpaRepository;

    @Override
    public ForumManager save(ForumManager forumUser) {
        return forumUserJpaRepository.save(ForumManagerEntity.from(forumUser)).toModel();
    }

    @Override
    public List<ForumManager> getForumManagers(String forumId) {
        return forumUserJpaRepository.getForumUsers(forumId).stream().map(ForumManagerEntity::toModel).collect(Collectors.toList());
    }


}
