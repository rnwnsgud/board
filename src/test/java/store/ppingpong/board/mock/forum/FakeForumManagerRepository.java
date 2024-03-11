package store.ppingpong.board.mock.forum;

import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.service.port.ForumManagerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeForumManagerRepository implements ForumManagerRepository {

    private final List<ForumManager> data = new ArrayList<>();
    private static long sequence = 0L;

    @Override
    public ForumManager save(ForumManager forumManager) {
        if (forumManager.getId()== null || forumManager.getId()==0){
            ForumManager mockForumUser = ForumManager.builder()
                    .id(sequence++)
                    .userId(forumManager.getUserId())
                    .forumId(forumManager.getForumId())
                    .forumManagerLevel(forumManager.getForumManagerLevel())
                    .build();
            data.add(mockForumUser);
            return forumManager;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), forumManager.getId()));
            data.add(forumManager);
            return forumManager;
        }
    }

    @Override
    public ForumManager findByForumId(String forumId) {
        return data.stream()
                .filter(forumManager -> forumManager.getForumId().equals(forumId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteByUserId(String forumId, long userId) {

    }
}
