package store.ppingpong.board.user.domain.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.ppingpong.board.common.domain.RandomHolder;
import store.ppingpong.board.user.application.CertificationService;
import store.ppingpong.board.user.application.UserService;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.dto.UserCreate;

@RequiredArgsConstructor
@Service
public class UserRegister {

    private final UserService userService;
    private final CertificationService certificationService;
    private final RandomHolder randomHolder;

    public User register(UserCreate userCreate) {
        String certificationCode = randomHolder.sixDigit();
        User user = userService.create(userCreate, certificationCode);
        certificationService.send(userCreate.getEmail(), user.getId(), certificationCode);
        return user;
    }
}
