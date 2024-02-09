package store.ppingpong.board.common.infrastructure;

import org.springframework.stereotype.Component;
import store.ppingpong.board.common.service.port.ClockHolder;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class SystemClockHolder implements ClockHolder {

    @Override
    public long mills() {
        return Clock.systemUTC().millis();
    }


}
