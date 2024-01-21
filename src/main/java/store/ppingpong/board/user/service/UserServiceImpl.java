package store.ppingpong.board.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.common.handler.exception.ResourceAlreadyExistException;
import store.ppingpong.board.common.handler.exception.ResourceNotFoundException;
import store.ppingpong.board.common.service.RedisService;
import store.ppingpong.board.common.service.port.ClockHolder;
import store.ppingpong.board.common.service.port.RandomHolder;
import store.ppingpong.board.user.controller.port.UserService;
import store.ppingpong.board.user.domain.LoginInfo;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.domain.UserInfo;
import store.ppingpong.board.user.dto.UserCreate;
import store.ppingpong.board.user.service.port.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;
    private final RedisService redisService;
    private final CertificationService certificationService;
    private final RandomHolder randomHolder;

    @Override // 유저가 존재하지 않다면, PENDING 상태인 유저를 생성한다.
    public User create(UserCreate userCreate) {

        Optional<User> userOP = userRepository.findByLoginId(userCreate.getLoginId());
        if (userOP.isPresent()) throw new ResourceAlreadyExistException("해당 유저가 이미 존재합니다.", userOP.get().getId());

        LoginInfo loginInfo = LoginInfo.of(userCreate, passwordEncoder);
        UserInfo userInfo = UserInfo.from(userCreate);

        User user = User.of(loginInfo, userInfo, clockHolder);
        user = userRepository.save(user);

        String certificationCode = randomHolder.sixDigit(); // 레디스서비스에 주입해서 쓰기

        redisService.setValueExpire(userInfo.getEmail(), certificationCode, 300);
        certificationService.send(userInfo.getEmail(), user.getId(), certificationCode);
        return user;
    }

    @Override // PENDING 상태인 유저가 메일인증을 성공하면 ACTIVE 상태로 전환한다.
    public void verifyEmail(Long id, String certificationCode) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
        redisService.verifyCode(user.getUserInfo().getEmail(), certificationCode);
        user.verified();
        userRepository.save(user);
    }
}
