package store.ppingpong.board.common.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import store.ppingpong.board.common.ResponseDto;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.common.handler.exception.resource.ResourceNotFoundException;
import store.ppingpong.board.common.infrastructure.RedisService;
import store.ppingpong.board.common.util.CustomResponseUtil;
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
                    .expiration(new Date(System.currentTimeMillis()+expiration)) // 현재시간에다 더해줘야함.
                    .signWith(secretKey)
                    .compact();
        } else jwtToken = Jwts.builder()
                .claim("category", category)
                .claim("id", loginUser.getUser().getId())
                .claim("role", loginUser.getUser().getUserInfo().getUserType().name())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expiration))
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

    public LoginUser verify(String token) {
        Long id = getId(token);
        String role = getRole(token);
        User user = User.valueOf(id, role);
        return new LoginUser(user);
    }

    public Boolean isExpired(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public String findToken(HttpServletRequest request, String name) {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        throw new ResourceNotFoundException("refresh token 이 존재하지 않습니다.");
    }

    public Cookie createCookie(String key, String value) {
        value = URLEncoder.encode(value, StandardCharsets.UTF_8);
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);
        return cookie;
    }

    public String reissue(String token, HttpServletResponse response) {
        try {
            isExpired(token);
        } catch (ExpiredJwtException e) {
            CustomResponseUtil.response(response, ResponseDto.of(-1, "토큰 기한이 만료되었습니다."), 401);
        }
        String category = getCategory(token);
        if (!category.equals("refresh")) {
            CustomResponseUtil.response(response, ResponseDto.of(-1, "refresh 토큰이 아닙니다."), 401);
        }
        LoginUser loginUser = verify(token);
        String accessToken = create("access",loginUser, 600000L);
        String refreshToken =  create("refresh", loginUser, 86400000L);
        redisService.setValueExpire(loginUser.getUsername(), refreshToken, 86400);
        response.addCookie(createCookie("refresh", refreshToken));
        return accessToken;
    }


}
