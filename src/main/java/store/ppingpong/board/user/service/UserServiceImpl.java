package store.ppingpong.board.user.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.common.handler.exception.ResourceAlreadyExistException;
import store.ppingpong.board.common.handler.exception.ResourceNotFoundException;
import store.ppingpong.board.common.service.port.ClockHolder;
import store.ppingpong.board.common.controller.port.InMemoryService;
import store.ppingpong.board.common.service.port.RandomHolder;
import store.ppingpong.board.user.controller.port.UserService;
import store.ppingpong.board.user.domain.LoginInfo;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.domain.UserInfo;
import store.ppingpong.board.user.dto.UserCreate;
import store.ppingpong.board.user.service.port.CustomPasswordEncoder;
import store.ppingpong.board.user.service.port.UserRepository;

import java.util.Optional;


@Builder
@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final CustomPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;
    private final InMemoryService inMemoryService;
    private final CertificationService certificationService;
    private final RandomHolder randomHolder;

    @Override // 유저가 존재하지 않다면, PENDING 상태인 유저를 생성한다.
    public User create(UserCreate userCreate) {
        checkUserExistence(userCreate);
        LoginInfo loginInfo = LoginInfo.of(userCreate, passwordEncoder);
        UserInfo userInfo = UserInfo.from(userCreate);
        User user = getByInfo(loginInfo, userInfo);
        sendEmail(userInfo, user);
        return user;
    }

    private void sendEmail(UserInfo userInfo, User user) {
        String certificationCode = randomHolder.sixDigit();
        inMemoryService.setValueExpire(userInfo.getEmail(), certificationCode, 300);
        certificationService.send(userInfo.getEmail(), user.getId(), certificationCode);
    }

    private void checkUserExistence(UserCreate userCreate) {
        Optional<User> userOP = userRepository.findByLoginId(userCreate.getLoginId());
        if (userOP.isPresent()) throw new ResourceAlreadyExistException("해당 유저가 이미 존재합니다.", userOP.get().getId());
    }

    @Override // PENDING 상태인 유저가 메일인증을 성공하면 ACTIVE 상태로 전환한다.
    public void verifyEmail(long id, String certificationCode) {
        User user = getById(id);
        inMemoryService.verifyCode(user.getUserInfo().getEmail(), certificationCode);
        user = user.verified();
        userRepository.save(user);
    }

    @Override
    public void login(long id) {
        User user = userRepository.getById(id);
        user = user.login(clockHolder);
        userRepository.save(user);
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    private User getByInfo(LoginInfo loginInfo, UserInfo userInfo) {
        User user = User.of(loginInfo, userInfo, clockHolder);
        user = userRepository.save(user);
        return user;
    }

    
}
