package store.ppingpong.board.forum.application;

import jakarta.persistence.EntityManager;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.common.handler.exception.resource.ResourceAlreadyExistException;
import store.ppingpong.board.common.handler.exception.resource.ResourceNotFoundException;
import store.ppingpong.board.common.domain.ClockLocalHolder;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.domain.ForumManagerLevel;
import store.ppingpong.board.forum.dto.ForumCreate;
import store.ppingpong.board.forum.dto.ForumUpdate;
import store.ppingpong.board.forum.application.port.ForumRepository;
import store.ppingpong.board.forum.application.port.ForumManagerRepository;
import store.ppingpong.board.user.domain.User;

import java.util.List;
import java.util.Optional;


@Builder
@RequiredArgsConstructor
@Transactional
@Service
public class ForumService {
    private final ForumRepository forumRepository;
    private final ForumManagerRepository forumManagerRepository;
    private final ClockLocalHolder clockLocalHolder;
    private final EntityManager em;

    public Forum create(ForumCreate forumCreate, User user) {
        String forumId = forumCreate.getForumId();
        boolean exists = forumRepository.existsById(forumId);
        if (exists) throw new ResourceAlreadyExistException("ForumId가 이미 사용중입니다.", forumId);
        Forum forum = forumRepository.create(Forum.of(forumCreate, clockLocalHolder, user.getUserInfo().getUserType()));
        ForumManager forumUser = ForumManager.of(forum.getForumId(), user.getId(), ForumManagerLevel.MANAGER);
        forumManagerRepository.save(forumUser);
        return forum;
    }


    @Transactional(readOnly = true)
    public List<Forum> getActiveList() {
        return forumRepository.getActiveList();
    }

    @Transactional(readOnly = true)
    public Forum getById(String forumId) {
        return forumRepository.getById(forumId);
    }
    public Forum modify(String forumId, ForumUpdate forumUpdate) {
        Forum forum = getById(forumId);
        em.clear();
        forum = forum.managerModify(forumUpdate, clockLocalHolder);
        forumRepository.modify(forum);
        return forum;
    }


}
