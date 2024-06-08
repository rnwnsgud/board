package store.ppingpong.board.common.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import store.ppingpong.board.common.ResponseDto;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.common.util.CustomResponseUtil;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Value("${jwt.access_header}")
    private String ACCESS_HEADER;

    @Value("${jwt.token_prefix}")
    private String TOKEN_PREFIX;

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final JwtProvider jwtProvider;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/api/users")) {
            chain.doFilter(request, response);
            return;
        }
        if (isHeaderVerify(request, response)) {
            String accessToken = request.getHeader(ACCESS_HEADER).replace(TOKEN_PREFIX, "");
            if (jwtProvider.isExpired(accessToken)) {
                CustomResponseUtil.response(response, ResponseDto.of(-1, "Access Token이 만료되었습니다."), 401);
                return;
            }
            LoginUser loginUser = jwtProvider.verify(accessToken);
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private boolean isHeaderVerify(HttpServletRequest request, HttpServletResponse response) {
        String accessHeader = request.getHeader(ACCESS_HEADER);
        return accessHeader != null && accessHeader.startsWith(TOKEN_PREFIX);
    }

}
