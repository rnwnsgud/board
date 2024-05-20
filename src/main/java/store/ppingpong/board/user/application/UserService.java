package store.ppingpong.board.user.application;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.common.handler.exception.resource.ResourceAlreadyExistException;
import store.ppingpong.board.common.handler.exception.resource.ResourceNotFoundException;
import store.ppingpong.board.common.domain.ClockHolder;
import store.ppingpong.board.common.domain.InMemoryService;
import store.ppingpong.board.common.domain.RandomHolder;
import store.ppingpong.board.user.domain.LoginInfo;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.domain.UserInfo;
import store.ppingpong.board.user.dto.UserCreate;
import store.ppingpong.board.user.application.port.CustomPasswordEncoder;
import store.ppingpong.board.user.application.port.UserRepository;

import java.util.Optional;


@Builder
@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final CustomPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;
    private final InMemoryService inMemoryService;
    private final CertificationService certificationService;

    public User create(UserCreate userCreate, String certificationCode) {
        checkUserExistence(userCreate);
        LoginInfo loginInfo = LoginInfo.of(userCreate, passwordEncoder);
        UserInfo userInfo = UserInfo.from(userCreate);
        User user = getByInfo(loginInfo, userInfo);
        inMemoryService.setValueExpire(userInfo.getEmail(), certificationCode, 300L);
        return user;
    }

    private User getByInfo(LoginInfo loginInfo, UserInfo userInfo) {
        User user = User.of(loginInfo, userInfo, clockHolder);
        user = userRepository.save(user);
        return user;
    }

    private void checkUserExistence(UserCreate userCreate) {
        Optional<User> userOP = userRepository.findByLoginId(userCreate.getLoginId());
        if (userOP.isPresent()) throw new ResourceAlreadyExistException("해당 유저가 이미 존재합니다.", userOP.get().getId());
    }

    public void verifyEmail(Long id, String certificationCode) {
        User user = getById(id);
        inMemoryService.verifyCode(user.getUserInfo().getEmail(), certificationCode);
        user = user.verified();
        userRepository.verify(user);
    }

    public void login(Long id) {
        User user = userRepository.getById(id);
        user = user.login(clockHolder);
        userRepository.login(user);
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }



    
}
