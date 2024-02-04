package store.ppingpong.board.forum.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.common.service.port.ClockHolder;
import store.ppingpong.board.forum.controller.port.ForumService;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.dto.ForumCreate;
import store.ppingpong.board.forum.service.port.ForumRepository;


@RequiredArgsConstructor
@Transactional
@Service
public class ForumServiceImpl implements ForumService {

    private final ForumRepository forumRepository;
    private final ClockHolder clockHolder;
    @Override
    public Forum create(ForumCreate forumCreate) {
        return forumRepository.save(Forum.from(forumCreate, clockHolder));
    }
}
