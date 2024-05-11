package store.ppingpong.board.common.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import store.ppingpong.board.common.handler.exception.join.CertificationCodeNotMatchedException;
import store.ppingpong.board.common.domain.InMemoryService;

import java.time.Duration;

@RequiredArgsConstructor
@Service // 레디스에서 접근해서 값을 가져오는것이 어떤 객체에도 들어가기 애매한 로직이니 서비스로 명명
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
