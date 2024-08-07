package store.ppingpong.board.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.ppingpong.board.common.ResponseDto;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.common.config.jwt.JwtProvider;
import store.ppingpong.board.common.infrastructure.RedisService;
import store.ppingpong.board.user.domain.service.UserRegister;
import store.ppingpong.board.user.dto.UserResponse;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.dto.UserCreate;
import store.ppingpong.board.user.application.UserService;

import java.util.Collection;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@Builder
@RestController
public class UserAccountController {
    private final UserService userService;
    private final UserRegister userRegister;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    @PostMapping
    public ResponseEntity<UserResponse> sendEmail(@Valid @RequestBody UserCreate userCreate, BindingResult bindingResult) {
        User user = userRegister.register(userCreate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponse.from(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> verifyEmail(
            @PathVariable("id") Long id,
            @RequestParam("certificationCode") String certificationCode) {
        userService.verifyEmail(id, certificationCode);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelRegister(@PathVariable("id") Long id) {
        userService.getById(id);
        userRegister.cancel(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("/reissueAccessToken")
    public ResponseEntity<?> reissueAccessToken(@AuthenticationPrincipal LoginUser loginUser, HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtProvider.findTokenInCookie(request, "refresh");
        String newAccessToken = jwtProvider.reissue(loginUser, refreshToken, response);
        return new ResponseEntity<>(ResponseDto.of(1, "access 토큰 재발급", newAccessToken), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response, @AuthenticationPrincipal LoginUser loginUser) {
        redisService.delete(loginUser.getUsername());
        jwtProvider.vacate("refresh", response);
        return new ResponseEntity<>(ResponseDto.of(1, "로그아웃 성공"), HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<Void> check(@AuthenticationPrincipal LoginUser loginUser) {

        Collection<? extends GrantedAuthority> authorities = loginUser.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            authority.getAuthority();
//            System.out.println("Authority: " + authority.getAuthority());
        }
//        System.out.println(loginUser.getUser().getUserInfo().getUserType());
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }


}
