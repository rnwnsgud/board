package store.ppingpong.board.mock.user;

import store.ppingpong.board.user.application.port.CustomPasswordEncoder;

public class FakePasswordEncoder implements CustomPasswordEncoder {
    @Override
    public String encode(String rawPassword) {
        return rawPassword;
    }
}
