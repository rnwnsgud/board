package store.ppingpong.board.common.infrastructure;

import org.springframework.stereotype.Component;
import store.ppingpong.board.common.domain.RandomHolder;

import java.util.Random;

@Component
public class SystemRandomHolder implements RandomHolder {
    @Override
    public String sixDigit() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
