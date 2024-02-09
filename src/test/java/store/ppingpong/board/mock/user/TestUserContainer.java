package store.ppingpong.board.mock.user;

import lombok.Builder;
import store.ppingpong.board.common.service.port.ClockHolder;
import store.ppingpong.board.common.controller.port.InMemoryService;
import store.ppingpong.board.common.service.port.RandomHolder;
import store.ppingpong.board.user.controller.UserAccountController;
import store.ppingpong.board.user.service.CertificationService;
import store.ppingpong.board.user.service.UserServiceImpl;
import store.ppingpong.board.user.service.port.CustomPasswordEncoder;
import store.ppingpong.board.user.service.port.EmailSender;
import store.ppingpong.board.user.service.port.UserRepository;

public class TestUserContainer {

    public final EmailSender emailSender;
    public final UserRepository userRepository;
    public final CertificationService certificationService;
    public final InMemoryService inMemoryService;
    public final CustomPasswordEncoder passwordEncoder;
    public final UserAccountController userController;

    @Builder
    public TestUserContainer(RandomHolder randomHolder, ClockHolder clockHolder) {
        this.emailSender = new FakeEmailSender();
        this.userRepository = new FakeUserRepository();
        this.certificationService = new CertificationService(emailSender);
        this.passwordEncoder = new FakePasswordEncoder();
        this.inMemoryService = new FakeRedisService();

        UserServiceImpl userService = UserServiceImpl.builder()
                .randomHolder(randomHolder)
                .clockHolder(clockHolder)
                .userRepository(userRepository)
                .certificationService(certificationService)
                .passwordEncoder(passwordEncoder)
                .inMemoryService(inMemoryService)
                .build();

        this.userController = UserAccountController.builder()
                .userService(userService)
                .build();
    }
}
