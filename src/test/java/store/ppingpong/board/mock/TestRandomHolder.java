package store.ppingpong.board.mock;

import lombok.RequiredArgsConstructor;
import store.ppingpong.board.common.service.port.RandomHolder;

@RequiredArgsConstructor
public class TestRandomHolder implements RandomHolder {

    private final String random;
    @Override
    public String sixDigit() {
        return random;
    }
}
