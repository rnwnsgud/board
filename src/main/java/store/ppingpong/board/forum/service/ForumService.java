package store.ppingpong.board.forum.service;

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
import store.ppingpong.board.forum.service.port.ForumRepository;
import store.ppingpong.board.forum.service.port.ForumManagerRepository;
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
        checkExistence(forumCreate);
        Forum forum = forumRepository.create(Forum.of(forumCreate, clockLocalHolder, user.getUserInfo().getUserType()));
        ForumManager forumUser = ForumManager.of(forum.getForumId(), user.getId(), ForumManagerLevel.MANAGER);
        forumManagerRepository.save(forumUser);
        return forum;
    }

    private void checkExistence(ForumCreate forumCreate) {
        Optional<Forum> forumOptional = forumRepository.findById(forumCreate.getForumId());
        if (forumOptional.isPresent()) throw new ResourceAlreadyExistException("이미 존재하는 Forum");
    }

    @Transactional(readOnly = true)
    public List<Forum> getActiveList() {
        return forumRepository.getActiveList();
    }

    @Transactional(readOnly = true)
    public Forum findById(String forumId) {
        return forumRepository.findById(forumId).orElseThrow(() -> new ResourceNotFoundException("Forums", forumId));
    }
    public Forum modify(String forumId, ForumUpdate forumUpdate) {
        Forum forum = findById(forumId);
        em.clear();
        forum = forum.managerModify(forumUpdate, clockLocalHolder);
        forumRepository.modify(forum);
        return forum;
    }


}
