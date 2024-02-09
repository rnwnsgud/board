package store.ppingpong.board.user.controller;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.user.controller.port.UserService;
import store.ppingpong.board.user.controller.response.UserResponse;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.dto.UserCreate;

import java.util.Collection;


@RequestMapping("/api/users")
@RequiredArgsConstructor
@Builder
@RestController
public class UserAccountController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> sendEmail(@RequestBody @Valid UserCreate userCreate) {
        User user = userService.create(userCreate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponse.from(user));
    }

    @GetMapping("/{id}/verify")
    public ResponseEntity<Void> verifyEmail(
            @PathVariable("id") long id,
            @RequestParam("certificationCode") String certificationCode) {
        userService.verifyEmail(id, certificationCode);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/check")
    public ResponseEntity<Void> check(@AuthenticationPrincipal LoginUser loginUser) {
        Collection<? extends GrantedAuthority> authorities = loginUser.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            authority.getAuthority();
            System.out.println("Authority: " + authority.getAuthority());
        }
        System.out.println(loginUser.getUser().getUserInfo().getUserEnum());
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
