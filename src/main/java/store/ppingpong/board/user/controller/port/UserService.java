package store.ppingpong.board.user.controller.port;

import org.springframework.stereotype.Service;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.dto.UserCreate;

@Service
public interface UserService {

    User create(UserCreate userCreate);

    void verifyEmail(Long id, String certificationCode);
}
