package store.ppingpong.board.common.service.port;

import java.time.Clock;
import java.time.LocalDateTime;

public interface ClockHolder {

    long mills();
    LocalDateTime localMills();
}
