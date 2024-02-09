package store.ppingpong.board.mock.user;

import store.ppingpong.board.common.handler.exception.CertificationCodeNotMatchedException;
import store.ppingpong.board.common.controller.port.InMemoryService;

import java.util.HashMap;
import java.util.Map;

public class FakeRedisService implements InMemoryService {

    Map<String, String> map = new HashMap<>();
    private Map<String, Long> expirationTimes = new HashMap<>();

    @Override
    public void setValueExpire(String key, String value, long duration) {
        map.put(key, value);
        expirationTimes.put(key, System.currentTimeMillis() + duration);
    }

    @Override
    public String getValue(String key) {
        if (isKeyValid(key)) {
            return map.get(key);
        } else {
            deleteValue(key); // 만료된 값은 삭제
            return null;
        }
    }

    @Override
    public void deleteValue(String key) {
        map.remove(key);
        expirationTimes.remove(key);
    }

    @Override
    public void verifyCode(String key, String certificationCode) {
        String storedCode = getValue(key);

        if (storedCode == null || !storedCode.equals(certificationCode)) {
            throw new CertificationCodeNotMatchedException();
        }
    }

    private boolean isKeyValid(String key) {
        Long expirationTime = expirationTimes.get(key);
        return expirationTime != null && System.currentTimeMillis() <= expirationTime;
    }
}
