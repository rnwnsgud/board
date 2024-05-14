package store.ppingpong.board.forum.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.ForumStatus;
import store.ppingpong.board.forum.application.port.ForumRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ForumRepositoryImpl implements ForumRepository {

    private final ForumJpaRepository forumJpaRepository;
    private final PostTypeRepository postTypeRepository;

    @Override
    public Forum create(Forum forum) {
        ForumEntity forumEntity = forumJpaRepository.save(ForumEntity.create(forum));
        PostTypeEntity postTypeEntity = new PostTypeEntity(1L, "일반");
        postTypeRepository.save(postTypeEntity);
        forumEntity.addPostType(postTypeEntity);
        return forumJpaRepository.save(ForumEntity.create(forum)).toModel();
    }

    @Override
    public List<Forum> getActiveList() {
        List<ForumEntity> forumEntities = forumJpaRepository.findByForumStatus(ForumStatus.ACTIVE);
        return forumEntities.stream().map(ForumEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public Optional<Forum> findById(String forumId) {
        return forumJpaRepository.findByForumId(forumId).map(ForumEntity::toModel);
    }

    @Override
    public void modify(Forum forum) {
        forumJpaRepository.modify(forum.getForumId(), forum.getIntroduction(), forum.getCategory(), forum.getLastModifiedAt());
    }
}
