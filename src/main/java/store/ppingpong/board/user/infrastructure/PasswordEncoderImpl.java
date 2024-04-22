package store.ppingpong.board.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import store.ppingpong.board.user.application.port.CustomPasswordEncoder;

@RequiredArgsConstructor
@Component
public class PasswordEncoderImpl implements CustomPasswordEncoder {

    private final PasswordEncoder passwordEncoder;
    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
