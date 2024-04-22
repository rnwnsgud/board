package store.ppingpong.board.common.domain;

public interface InMemoryService {

    void setValueExpire(String key, String value, long duration);

    String getValue(String key);

    void verifyCode(String key, String certificationCode);

    Long getExpirationTime(String key);


}
