package store.ppingpong.board.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import store.ppingpong.board.common.util.CustomResponseUtil;

import static store.ppingpong.board.common.util.CustomResponseUtil.fail;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers((headerConfig) ->
                headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
        );

        http.csrf((AbstractHttpConfigurer::disable));
        http.cors((cors -> cors.configurationSource(configurationSource())));
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic((AbstractHttpConfigurer::disable));
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

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    public static class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
//        @Override
//        public void configure(HttpSecurity builder) throws Exception { // 스프링 시큐리티에서 사용되는 구성을 위한 빌더 클래스
//            builder.addFilter(jwtAuthenticationFilter);
//            builder.addFilter(jwtAuthorizationFilter);
//            super.configure(builder);
//        }
//    }


    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*"); // 모든 ip 주소 허용 (프론트 엔드 IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트의 쿠키 요청 허용
//        configuration.addExposedHeader("ACCESS_TOKEN"); // 브라우저에 Authorization 헤더 노출, 클라이언트가 저장하기 위해서
//        configuration.addExposedHeader("REFRESH_TOKEN"); // 브라우저에 REFRESH_TOKEN 헤더 노출, 클라이언트가 저장하기 위해서
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 주소요청에 위 설정을 넣어주겠다.
        return source;
    }
}

