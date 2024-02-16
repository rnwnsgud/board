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
    public ForumManager save(ForumManager forumUser) {
        if (forumUser.getId()== null || forumUser.getId()==0){
            ForumManager mockForumUser = ForumManager.builder()
                    .id(sequence++)
                    .user(forumUser.getUser())
                    .forum(forumUser.getForum())
                    .forumUserLevel(forumUser.getForumUserLevel())
                    .build();

            data.add(mockForumUser);
            return forumUser;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), forumUser.getId()));
            data.add(forumUser);
            return forumUser;
        }
    }
}
