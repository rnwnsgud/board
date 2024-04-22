package store.ppingpong.board.mock.forum;

import lombok.RequiredArgsConstructor;
import store.ppingpong.board.common.domain.ClockLocalHolder;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TestClockLocalHolder implements ClockLocalHolder {

    private final LocalDateTime localDateTime;
    @Override
    public LocalDateTime localMills() {
        return localDateTime;
    }
}
