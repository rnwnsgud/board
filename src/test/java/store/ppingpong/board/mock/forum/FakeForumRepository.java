package store.ppingpong.board.mock.forum;

import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.ForumStatus;
import store.ppingpong.board.forum.service.port.ForumRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeForumRepository implements ForumRepository {

    private final List<Forum> data = new ArrayList<>();
    @Override
    public Forum save(Forum forum) {
        if (forum.getForumId() == null || forum.getForumId().isEmpty()) {
            Forum mockForum = Forum.builder()
                    .forumId(forum.getForumId())
                    .forumStatus(forum.getForumStatus())
                    .introduction(forum.getIntroduction())
                    .name(forum.getName())
                    .createdAt(LocalDateTime.MIN)
                    .category(forum.getCategory())
                    .build();
            data.add(mockForum);
            return mockForum;
        } else {
            data.removeIf(item -> Objects.equals(item.getForumId(), forum.getForumId()));
            data.add(forum);
            return forum;
        }

    }

    @Override
    public List<Forum> getActiveList() {
        return data.stream()
                .filter(item -> item.getForumStatus() == ForumStatus.ACTIVE)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Forum> findById(String forumId) {
        return Optional.empty();
    }
}
