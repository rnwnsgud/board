package store.ppingpong.board.common.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.user.domain.User;


import java.util.Date;

@Component
public class JwtProvider {


    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.token_prefix}")
    private String TOKEN_PREFIX;

    @Value("${jwt.expiration_time}")
    private long expiration;

    public String accessTokenCreate(LoginUser loginUser) {

        String jwtToken = JWT.create()
                .withSubject("accessToken")
                .withExpiresAt(new Date(System.currentTimeMillis()+expiration)) // 현재시간에다 더해줘야함.
                .withClaim("id", loginUser.getUser().getId())
                .withClaim("role", loginUser.getUser().getUserInfo().getUserType().name())
                .sign(Algorithm.HMAC512(SECRET));
        return TOKEN_PREFIX + jwtToken;
    }

    public LoginUser accessTokenVerify(String token) {

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET)).build().verify(token);
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();
        User user = User.valueOf(id, role);
        return new LoginUser(user);
    }
}
