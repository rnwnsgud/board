package store.ppingpong.board.mock.user;

import lombok.RequiredArgsConstructor;
import store.ppingpong.board.common.domain.RandomHolder;

@RequiredArgsConstructor
public class TestRandomHolder implements RandomHolder {

    private final String random;
    @Override
    public String sixDigit() {
        return random;
    }
}
