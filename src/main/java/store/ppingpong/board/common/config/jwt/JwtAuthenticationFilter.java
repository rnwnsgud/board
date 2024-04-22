package store.ppingpong.board.common.config.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.dto.UserLoginReq;
import store.ppingpong.board.user.dto.UserLoginResp;
import store.ppingpong.board.user.application.UserService;


import java.io.IOException;

import static store.ppingpong.board.common.util.CustomDateUtil.convertToStringForHuman;
import static store.ppingpong.board.common.util.CustomResponseUtil.success;


@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserService userService;
    @Value("${jwt.access_header}")
    private String accessHeader;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
    JwtProvider jwtProvider, UserService userService) {
        super(authenticationManager);
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

        } catch (IOException e) {
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        String accessToken = jwtProvider.accessTokenCreate(loginUser);
        response.addHeader(accessHeader, accessToken);
        User user = loginUser.getUser();
        userService.login(user.getId());
        String createdAt = convertToStringForHuman(user.getCreatedAt(), "yyyy-MM-dd HH:mm:ss");
        UserLoginResp userLoginResp = new UserLoginResp(user.getId(), user.getLoginInfo().getLoginId(), createdAt, accessToken);
        success(response, userLoginResp);
    }
}
