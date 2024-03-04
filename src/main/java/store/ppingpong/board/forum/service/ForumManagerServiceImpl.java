package store.ppingpong.board.forum.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.forum.controller.port.ForumManagerService;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.service.port.ForumManagerRepository;

@Builder
@RequiredArgsConstructor
@Transactional
@Service
public class ForumManagerServiceImpl implements ForumManagerService {

    private final ForumManagerRepository forumManagerRepository;

    @Override
    public ForumManager findForumManager(String forumId) {
        return forumManagerRepository.getManagerByForumId(forumId);
    }

    @Override
    public ForumManager save(ForumManager forumManager) {
        return forumManagerRepository.save(forumManager);
    }

}
