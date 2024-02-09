package store.ppingpong.board.forum.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.common.service.port.ClockHolder;
import store.ppingpong.board.common.service.port.ClockLocalHolder;
import store.ppingpong.board.forum.controller.port.ForumService;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.ForumUser;
import store.ppingpong.board.forum.domain.ForumUserLevel;
import store.ppingpong.board.forum.dto.ForumCreate;
import store.ppingpong.board.forum.service.port.ForumRepository;
import store.ppingpong.board.forum.service.port.ForumUserRepository;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.domain.UserEnum;

import java.util.List;


@Builder
@RequiredArgsConstructor
@Transactional
@Service
public class ForumServiceImpl implements ForumService {

    private final ForumRepository forumRepository;
    private final ForumUserRepository forumUserRepository;
    private final ClockLocalHolder clockLocalHolder;
    @Override
    public Forum create(ForumCreate forumCreate, User user) {
        Forum forum = forumRepository.save(Forum.valueOf(forumCreate, clockLocalHolder, user.getUserInfo().getUserEnum()));
        ForumUser forumUser = ForumUser.builder()
                .forum(forum)
                .user(user)
                .forumUserLevel(ForumUserLevel.MANAGER).build();
        forumUserRepository.save(forumUser);
        return forum;
    }

    @Override
    public List<Forum> getActiveList() {
        return forumRepository.getActiveList();
    }


}
