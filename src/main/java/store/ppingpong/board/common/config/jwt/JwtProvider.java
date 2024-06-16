package store.ppingpong.board.common.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.common.handler.exception.jwt.RefreshTokenExpired;
import store.ppingpong.board.common.handler.exception.jwt.TokenInvalidException;
import store.ppingpong.board.common.handler.exception.resource.ResourceNotFoundException;
import store.ppingpong.board.common.infrastructure.RedisService;
import store.ppingpong.board.user.domain.User;


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {


    @Value("${jwt.token_prefix}")
    private String TOKEN_PREFIX;
    private final RedisService redisService;
    private final SecretKey secretKey;

    public JwtProvider(@Value("${jwt.secret}") String secret, RedisService redisService) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.redisService = redisService;
    }

    public String create(String category, LoginUser loginUser, long expiration) {
        String jwtToken;
        if (category.equals("access")) {
            jwtToken = Jwts.builder()
                    .claim("category", category)
                    .claim("id", loginUser.getUser().getId())
                    .claim("email", loginUser.getUsername())
                    .claim("role", loginUser.getUser().getUserInfo().getUserType().name())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+expiration))
                    .signWith(secretKey)
                    .compact();
        } else jwtToken = Jwts.builder()
                .claim("category", category)
                .claim("id", loginUser.getUser().getId())
                .claim("role", loginUser.getUser().getUserInfo().getUserType().name())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(secretKey)
                .compact();

        return TOKEN_PREFIX + jwtToken;
    }
    
    private Long getId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("id", Long.class);
    }

    private String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    private String getCategory(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    private String getEmail(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }

    public LoginUser verifyAccessToken(String token) {
        Long id = getId(token);
        String role = getRole(token);
        String email = getEmail(token);
        User user = User.valueOf(id, role, email);
        return new LoginUser(user);
    }

    public Boolean isExpired(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public String findTokenInCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        throw new ResourceNotFoundException("refresh token 이 존재하지 않습니다.");
    }

    public Cookie cookieWithRefreshToken(String key, String value) {
        String replacedValue = value.replace(TOKEN_PREFIX, "");
        replacedValue = URLEncoder.encode(replacedValue, StandardCharsets.UTF_8);
        Cookie cookie = new Cookie(key, replacedValue);
        cookie.setMaxAge(24*60*60);
//        cookie.setSecure(true); https
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    public void vacate(String name, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public String reissue(LoginUser loginUser, String refreshToken, HttpServletResponse response) {
        verifyRefreshToken(refreshToken, loginUser.getUserId());
        String accessToken = create("access",loginUser, 600000L);
        String newRefreshToken =  create("refresh", loginUser, 86400000L);
        redisService.setValueExpire(loginUser.getUsername(), newRefreshToken, 86400);
        response.addCookie(cookieWithRefreshToken("refresh", newRefreshToken));
        return accessToken;
    }

    private void verifyRefreshToken(String refreshToken, Long userId) {
        if (isExpired(refreshToken)) throw new RefreshTokenExpired();
        String category = getCategory(refreshToken);
        if (!category.equals("refresh")) throw new TokenInvalidException("토큰이 유효하지 않습니다.");
        Long id = getId(refreshToken);
        if (!id.equals(userId)) throw new TokenInvalidException("토큰이 유효하지 않습니다.");
    }


}
