package store.ppingpong.board.common.config.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import store.ppingpong.board.common.ResponseDto;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.common.handler.exception.resource.ResourceNotVerifiedException;
import store.ppingpong.board.common.infrastructure.RedisService;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.dto.UserLoginReq;
import store.ppingpong.board.user.dto.UserLoginResp;
import store.ppingpong.board.user.application.UserService;


import java.io.IOException;

import static store.ppingpong.board.common.util.CustomDateUtil.convertToStringForHuman;
import static store.ppingpong.board.common.util.CustomResponseUtil.response;


@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final RedisService redisService;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JwtProvider jwtProvider, UserService userService, RedisService redisService) {
        super(authenticationManager);
        this.redisService = redisService;
        setFilterProcessesUrl("/api/users/login");
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper om = new ObjectMapper();
        try {
            UserLoginReq userLogin = om.readValue(request.getInputStream(), UserLoginReq.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userLogin.getLoginId(), userLogin.getRawPassword());

            return authenticationManager.authenticate(authenticationToken);
        } catch (ResourceNotVerifiedException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        String accessToken = jwtProvider.create("access",loginUser, 600000L);
        String refreshToken =  jwtProvider.create("refresh", loginUser, 86400000L);
        response.addCookie(jwtProvider.cookieWithRefreshToken("refresh", refreshToken));
//        response.addHeader();
        redisService.setValueExpire(loginUser.getUsername(), refreshToken, 86400);
        User user = loginUser.getUser();
        userService.login(user.getId());
        String createdAt = convertToStringForHuman(user.getCreatedAt(), "yyyy-MM-dd HH:mm:ss");
        UserLoginResp userLoginResp = new UserLoginResp(user.getId(), user.getLoginInfo().getLoginId(), createdAt, accessToken, refreshToken);
        response(response, userLoginResp, 200);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response(response, ResponseDto.of(-1, failed.getMessage()), 401);
    }
}
