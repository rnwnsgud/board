package store.ppingpong.board.mock.user;

import store.ppingpong.board.common.handler.exception.resource.ResourceNotFoundException;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.application.port.UserRepository;

import java.util.*;

public class FakeUserRepository implements UserRepository {

    private final List<User> data = new ArrayList<>();
    private static long sequence = 0L;

    @Override
    public Optional<User> findById(Long id) {
        return data.stream().filter(item -> item.getId().equals(id)).findAny(); // 아무거나 하나 반환
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        return data.stream().filter(item -> item.getLoginInfo().getLoginId().equals(loginId)).findAny();
    }

    @Override
    public User getById(Long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Override
    public User save(User user) {
        if (user.getId() == null || user.getId() == 0) {
            User mockUser = User.builder()
                    .id(++sequence)
                    .userInfo(user.getUserInfo())
                    .loginInfo(user.getLoginInfo())
                    .userStatus(user.getUserStatus())
                    .createdAt(100L)
                    .lastLoginAt(user.getLastLoginAt())
                    .build();
            data.add(mockUser);
            return mockUser;

        } else {
            data.removeIf(item -> Objects.equals(item.getId(), user.getId())); // 변경 감지를 구현
            data.add(user);
            return user;
        }

    }

    @Override
    public User findForumManager(String forumId) {
        return null;
    }

    @Override
    public List<User> findForumAssistant(String forumId) {
        return null;
    }

    @Override
    public User findManagerOrAssistant(String forumId, Long userId) {
        return null;
    }

    @Override
    public void login(User user) {

    }

    @Override
    public void verify(User user) {
        User getUser = getById(user.getId());
        save(getUser.verified());
    }

    @Override
    public boolean existsById(Long id) {
        return true;
    }

    @Override
    public void deleteById(Long id) {

    }
}
