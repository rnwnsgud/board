package store.ppingpong.board.forum.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.service.port.ForumManagerRepository;

@Builder
@RequiredArgsConstructor
@Transactional
@Service
public class ForumManagerService {

    private final ForumManagerRepository forumManagerRepository;

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

}
