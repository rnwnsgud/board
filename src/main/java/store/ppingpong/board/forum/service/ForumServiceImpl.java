package store.ppingpong.board.forum.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.common.handler.exception.ResourceNotFoundException;
import store.ppingpong.board.common.service.port.ClockLocalHolder;
import store.ppingpong.board.forum.controller.port.ForumService;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.domain.ForumManagerLevel;
import store.ppingpong.board.forum.dto.ForumCreate;
import store.ppingpong.board.forum.service.port.ForumRepository;
import store.ppingpong.board.forum.service.port.ForumManagerRepository;
import store.ppingpong.board.user.domain.User;

import java.util.List;


@Builder
@RequiredArgsConstructor
@Transactional
@Service
public class ForumServiceImpl implements ForumService {


    private final ForumRepository forumRepository;
    private final ForumManagerRepository forumManagerRepository;
    private final ClockLocalHolder clockLocalHolder;
    @Override
    public Forum create(ForumCreate forumCreate, User user) {
        Forum forum = forumRepository.save(Forum.of(forumCreate, clockLocalHolder, user.getUserInfo().getUserEnum()));
        ForumManager forumUser = ForumManager.builder()
                .forum(forum)
                .user(user)
                .forumUserLevel(ForumManagerLevel.MANAGER).build();
        forumManagerRepository.save(forumUser);
        return forum;
    }

    @Override
    public List<Forum> getActiveList() {
        return forumRepository.getActiveList();
    }

    @Override
    public Forum findById(String forumId) {
        return forumRepository.findById(forumId).orElseThrow(() -> new ResourceNotFoundException("Forums", forumId));
    }




}
