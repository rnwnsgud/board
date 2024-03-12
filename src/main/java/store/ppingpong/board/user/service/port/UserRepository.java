package store.ppingpong.board.user.service.port;

import store.ppingpong.board.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(long id);

    Optional<User> findByLoginId(String loginId);

    User getById(long id);

    User save(User user);

    User findForumManager(String forumId);

    List<User> findForumAssistant(String forumId);

    User findManagerOrAssistant(String forumId, long userId);
    void login(User user);

    void verify(User user);
}
