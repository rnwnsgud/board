package store.ppingpong.board.mock.user;

import store.ppingpong.board.common.handler.exception.CertificationCodeNotMatchedException;
import store.ppingpong.board.common.service.port.InMemoryService;

import java.util.HashMap;
import java.util.Map;

public class FakeRedisService implements InMemoryService {

    Map<String, String> map = new HashMap<>();
    private final Map<String, Long> expirationTimes = new HashMap<>();

    @Override
    public void setValueExpire(String key, String value, long duration) {
        map.put(key, value);
        expirationTimes.put(key, duration);
    }

    @Override
    public String getValue(String key) {
        return map.get(key);
    }

    @Override
    public void verifyCode(String key, String certificationCode) {
        String storedCode = getValue(key);
        if (storedCode == null || !storedCode.equals(certificationCode)) {
            throw new CertificationCodeNotMatchedException();
        }
    }

    @Override
    public Long getExpirationTime(String key) {
        return expirationTimes.get(key);
    }

}
