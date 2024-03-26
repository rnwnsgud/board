package store.ppingpong.board.post.controller;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.ppingpong.board.common.ResponseDto;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.dto.PostCreate;
import store.ppingpong.board.post.dto.PostResponse;
import store.ppingpong.board.post.service.PostService;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.service.port.UserRepository;

@RequiredArgsConstructor
@Builder
@RequestMapping(("/api/s/post"))
@RestController
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    @PostMapping("/{forumId}")
    public ResponseEntity<ResponseDto<PostResponse>> create(@RequestBody @Valid PostCreate postCreate, BindingResult bindingResult,
                                    @PathVariable("forumId") String forumId, @AuthenticationPrincipal LoginUser loginUser) {
        Long userId = loginUser.getUser().getId();
        Post post = postService.create(postCreate, userId, forumId);
        return new ResponseEntity<>(ResponseDto.of(1, "게시글 생성 성공", PostResponse.from(post)), HttpStatus.CREATED);
    }
}
