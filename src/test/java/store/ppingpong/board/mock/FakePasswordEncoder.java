package store.ppingpong.board.mock;

import store.ppingpong.board.user.service.port.CustomPasswordEncoder;

public class FakePasswordEncoder implements CustomPasswordEncoder {
    @Override
    public String encode(String rawPassword) {
        return rawPassword;
    }
}
