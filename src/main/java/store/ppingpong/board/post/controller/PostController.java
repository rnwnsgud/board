package store.ppingpong.board.post.controller;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.ppingpong.board.common.ResponseDto;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.domain.PostWithImages;
import store.ppingpong.board.post.dto.PostCreate;
import store.ppingpong.board.post.dto.PostDetailResponse;
import store.ppingpong.board.post.dto.PostResponse;
import store.ppingpong.board.post.application.PostService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Builder
@RequestMapping(("/api/s/post"))
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/{forumId}")
    public ResponseEntity<ResponseDto<PostResponse>> create(@RequestPart(value = "postCreate") @Valid PostCreate postCreate, BindingResult bindingResult,
                                                            @RequestPart(value = "images", required = false) List<MultipartFile> images, @PathVariable("forumId") String forumId,
                                                            @AuthenticationPrincipal LoginUser loginUser) throws IOException {
        Long userId = loginUser.getUser().getId();
        PostWithImages postWithImages = postService.create(postCreate, userId, forumId, images);
        return new ResponseEntity<>(ResponseDto.of(1, "게시글 생성 성공", PostResponse.from(postWithImages)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<PostDetailResponse>> get(@PathVariable("id") long id, @AuthenticationPrincipal LoginUser loginUser) {
        Long userId = loginUser.getUser().getId();
        Post post = postService.findById(id, userId);
        return new ResponseEntity<>(ResponseDto.of(1, "게시글 조회 성공", PostDetailResponse.from(post)), HttpStatus.OK);
    }
}
