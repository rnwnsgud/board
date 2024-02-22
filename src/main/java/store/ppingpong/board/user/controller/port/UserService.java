package store.ppingpong.board.user.controller.port;

import org.springframework.stereotype.Service;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.dto.UserCreate;

import java.util.Optional;

@Service
public interface UserService {

    User create(UserCreate userCreate);

    void verifyEmail(long id, String certificationCode);

    void login(long id);

    User getById(long id);

}
