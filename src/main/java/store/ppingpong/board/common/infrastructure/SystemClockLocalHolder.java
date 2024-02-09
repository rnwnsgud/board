package store.ppingpong.board.common.infrastructure;

import org.springframework.stereotype.Component;
import store.ppingpong.board.common.service.port.ClockLocalHolder;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class SystemClockLocalHolder implements ClockLocalHolder {
    @Override
    public LocalDateTime localMills() {
        return LocalDateTime.now(Clock.systemUTC());
    }

}
