package store.ppingpong.board.common.infrastructure;

import org.springframework.stereotype.Component;
import store.ppingpong.board.common.domain.ClockHolder;

import java.time.Clock;

@Component
public class SystemClockHolder implements ClockHolder {

    @Override
    public long mills() {
        return Clock.systemUTC().millis();
    }


}
