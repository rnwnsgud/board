package store.ppingpong.board.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.common.handler.exception.resource.ResourceNotFoundException;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.application.port.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        return userJpaRepository.findByLoginInfoLoginId(loginId).map(UserEntity::toModel);
    }

    @Override
    public User getById(Long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.from(user)).toModel();
    }

    @Override
    public User findForumManager(String forumId) {
        return userJpaRepository.findForumManager(forumId).toModel();
    }

    @Override
    public List<User> findForumAssistant(String forumId) {
        return userJpaRepository.findForumAssistants(forumId).stream()
                .map(UserEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public User findManagerOrAssistant(String forumId, Long userId) {
        return userJpaRepository.findManagerOrAssistant(forumId, userId).orElseThrow(() -> new ResourceNotFoundException("해당 포럼 관리자 리스트", userId)).toModel();
    }

    @Override
    public void login(User user) {
        userJpaRepository.login(user.getId(), user.getLastLoginAt());
    }

    @Override
    public void verify(User user) {
        userJpaRepository.verify(user.getId(), user.getUserStatus());
    }

    @Override
    public boolean existsById(Long id) {
        return userJpaRepository.existsById(id);
    }

}
