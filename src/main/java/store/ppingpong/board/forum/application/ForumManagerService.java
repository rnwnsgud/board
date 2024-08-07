package store.ppingpong.board.forum.application;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.dto.ForumPostTypeResponse;
import store.ppingpong.board.forum.infrastructure.ForumEntity;
import store.ppingpong.board.forum.infrastructure.PostTypeEntity;
import store.ppingpong.board.forum.infrastructure.PostTypeRepository;
import store.ppingpong.board.forum.application.port.ForumManagerRepository;

import java.util.List;

@Builder
@RequiredArgsConstructor
@Transactional
@Service
public class ForumManagerService {

    private final ForumManagerRepository forumManagerRepository;
    private final PostTypeRepository postTypeRepository;

    @Transactional(readOnly = true)
    public ForumManager findManagerByForumId(String forumId) {
        return forumManagerRepository.findManagerByForumId(forumId);
    }

    public ForumManager create(ForumManager forumManager) {
        return forumManagerRepository.save(forumManager);
    }

    public void delete(String forumId, Long userId) {
        forumManagerRepository.deleteByUserId(forumId, userId);
    }

    public ForumPostTypeResponse createPostType(String[] postTypes, Forum forum) {
        List<PostTypeEntity> createdPostTypes = postTypeRepository.findByNameInAndCreate(postTypes, forum);
        ForumEntity forumEntity = ForumEntity.from(forum);
        forumEntity.addPostTypes(createdPostTypes);
        return new ForumPostTypeResponse(forumEntity.getPostTypes());
    }

}
