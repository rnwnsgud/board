package store.ppingpong.board.mock;

import lombok.RequiredArgsConstructor;
import store.ppingpong.board.common.service.port.ClockHolder;

@RequiredArgsConstructor
public class TestClockHolder implements ClockHolder {

    private final long millis;
    @Override
    public long mills() {
        return millis;
    }
}