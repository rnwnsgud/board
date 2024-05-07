package store.ppingpong.board.forum.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.domain.PostType;
import store.ppingpong.board.forum.dto.ForumPostTypeResponse;
import store.ppingpong.board.forum.infrastructure.PostTypeRepository;
import store.ppingpong.board.forum.service.port.ForumManagerRepository;

import java.util.List;
import java.util.stream.Collectors;

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
        List<PostType> createdTypes = postTypeRepository.findByNameInAndCreate(postTypes, forum);
        forum.addPostTypes(createdTypes);
        return new ForumPostTypeResponse(forum.getPostTypes().stream().map(PostType::getName).collect(Collectors.toList()));
    }

}
