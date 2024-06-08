package store.ppingpong.board.common.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import store.ppingpong.board.common.handler.exception.join.CertificationCodeNotMatchedException;
import store.ppingpong.board.common.domain.InMemoryService;

import java.time.Duration;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class RedisService implements InMemoryService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void setValueExpire(String key, String value, long duration) {

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value, Duration.ofSeconds(duration));
    }

    @Override
    public String getValue(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    @Override
    public void verifyCode(String key, String certificationCode) {
        String value = getValue(key);
        if (!certificationCode.equals(value)) throw new CertificationCodeNotMatchedException();
    }

    @Override
    public Long getExpirationTime(String key) {
        return redisTemplate.getExpire(key);
    }

}
