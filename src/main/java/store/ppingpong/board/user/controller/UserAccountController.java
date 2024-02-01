package store.ppingpong.board.user.controller;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ppingpong.board.user.controller.port.UserService;
import store.ppingpong.board.user.controller.response.UserResponse;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.dto.UserCreate;


@RequestMapping("/api/users")
@RequiredArgsConstructor
@Builder
@RestController
public class UserAccountController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> sendEmail(@RequestBody UserCreate userCreate) {
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
}
