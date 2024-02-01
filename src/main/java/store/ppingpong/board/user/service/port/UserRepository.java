package store.ppingpong.board.user.service.port;

import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.infrastructure.UserEntity;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(long id);

    Optional<User> findByLoginId(String loginId);

    User getById(long id);

    User save(User user);
}
