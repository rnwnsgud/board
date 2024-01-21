package store.ppingpong.board.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import store.ppingpong.board.common.handler.exception.CertificationCodeNotMatchedException;

import java.time.Duration;

@RequiredArgsConstructor
@Service // 레디스에서 접근해서 값을 가져오는것이 어떤 객체에도 들어가기 애매한 로직이니 서비스로 명명
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    public void setValue(String key, String value) {

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    public void setValueExpire(String key, String value, long duration) {

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value, Duration.ofSeconds(duration));
    }

    private String getValue(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    public void verifyCode(String email, String certificationCode) {
        System.out.println(email);
        String value = getValue(email);
        System.out.println(value);
        if (!certificationCode.equals(value)) throw new CertificationCodeNotMatchedException();
    }




}
