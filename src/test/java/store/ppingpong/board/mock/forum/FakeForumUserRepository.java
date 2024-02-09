package store.ppingpong.board.mock.forum;

import store.ppingpong.board.forum.domain.ForumUser;
import store.ppingpong.board.forum.service.port.ForumUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeForumUserRepository implements ForumUserRepository {

    private final List<ForumUser> data = new ArrayList<>();
    private static long sequence = 0L;
    @Override
    public ForumUser save(ForumUser forumUser) {
        if (forumUser.getId()== null || forumUser.getId()==0){
            ForumUser mockForumUser = ForumUser.builder()
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
