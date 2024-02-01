package store.ppingpong.board.common.controller.port;

public interface InMemoryService {

    void setValueExpire(String key, String value, long duration);

    String getValue(String key);

    void deleteValue(String key);

    void verifyCode(String key, String certificationCode);


}
