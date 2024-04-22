package store.ppingpong.board.mock.user;

import lombok.RequiredArgsConstructor;
import store.ppingpong.board.common.domain.ClockHolder;

@RequiredArgsConstructor
public class TestClockHolder implements ClockHolder {

    private final long millis;
    @Override
    public long mills() {
        return millis;
    }

}
