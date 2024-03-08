package store.ppingpong.board.common.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import store.ppingpong.board.common.config.jwt.JwtAuthenticationFilter;
import store.ppingpong.board.common.config.jwt.JwtAuthorizationFilter;
import store.ppingpong.board.common.config.jwt.JwtProvider;


import static store.ppingpong.board.common.util.CustomResponseUtil.fail;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    // 생성자주입으로 가져오려니 다음 순환참조가 생김
    // jwtAuthenticatiorFilter -> AuthenticationManager(AuthConfig) -> SecurityConfig

    // 빈등록순서때문에 발생하는거로 짐작해서, filterChain 파라미터에 선언해서 빈이 등록된걸 가져오도록 바꿈

    // JwtAuthenticationFilter 정상동작



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter,
                                           JwtAuthorizationFilter jwtAuthorizationFilter) throws Exception {
        log.debug("filterChain 등록");
        http.headers((headerConfig) ->
                headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
        );

        http.csrf((AbstractHttpConfigurer::disable));
        http.cors((cors -> cors.configurationSource(configurationSource())));
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic((AbstractHttpConfigurer::disable));

        http.addFilterBefore(jwtAuthorizationFilter, BasicAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);



        http.exceptionHandling((exception) -> exception.authenticationEntryPoint((request, response, e) -> {
            log.error("error : " + e.getMessage());
            fail(response, "로그인을 진행 해주세요 .", HttpStatus.UNAUTHORIZED);
        }));
        http.exceptionHandling((exception) -> exception.accessDeniedHandler((request, response, e) -> {
            log.error("error : " + e.getMessage());
            fail(response, "권한이 없습니다.", HttpStatus.FORBIDDEN);
        }));
        http.authorizeHttpRequests((authorize) ->
                authorize
                        .requestMatchers("/api/s/**")
                        .authenticated()
                        .anyRequest().permitAll());

        return http.build();

    }


    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("ACCESS_TOKEN");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 주소요청에 위 설정을 넣어주겠다.
        return source;
    }
}

