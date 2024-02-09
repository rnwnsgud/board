package store.ppingpong.board.mock.user;

import lombok.RequiredArgsConstructor;
import store.ppingpong.board.common.service.port.ClockHolder;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TestClockHolder implements ClockHolder {

    private final long millis;
    private final LocalDateTime localDateTime;
    @Override
    public long mills() {
        return millis;
    }

    @Override
    public LocalDateTime localMills() {
        return localDateTime;
    }

}
