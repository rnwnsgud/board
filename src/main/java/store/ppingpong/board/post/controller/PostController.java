package store.ppingpong.board.post.controller;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.ppingpong.board.common.ResponseDto;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.forum.application.ForumService;
import store.ppingpong.board.post.application.port.PostRepository;
import store.ppingpong.board.post.domain.PostWithImages;
import store.ppingpong.board.post.dto.*;
import store.ppingpong.board.post.application.PostService;
import store.ppingpong.board.reaction.application.ReactionService;
import store.ppingpong.board.reaction.domain.ReactionType;
import store.ppingpong.board.reaction.domain.TargetType;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Builder
@RestController
public class PostController {

    private final PostService postService;
    private final ForumService forumService;
    private final ReactionService reactionService;
    private final PostRepository postRepository;

    @PostMapping(value = "/api/s/post/{forumId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto<PostResponse>> create(@RequestPart(value = "postCreate") @Valid PostCreateRequest postCreateRequest, BindingResult bindingResult,
                                                            @RequestPart(value = "images", required = false) List<MultipartFile> images, @PathVariable("forumId") String forumId,
                                                            @AuthenticationPrincipal LoginUser loginUser) throws IOException {
        forumService.getById(forumId);
        Long userId = loginUser.getUserId();
        PostCreateResponse postCreateResponse = postService.create(postCreateRequest, userId, forumId, images);
        return new ResponseEntity<>(ResponseDto.of(1, "게시글 생성 성공", PostResponse.from(postCreateResponse)), HttpStatus.CREATED);
    }

    @GetMapping("/api/post/{id}")
    public ResponseEntity<ResponseDto<PostDetailResponse>> get(@PathVariable("id") long id, @AuthenticationPrincipal LoginUser loginUser) {
        PostWithImages postWithImages = postService.getById(id, loginUser);
        return new ResponseEntity<>(ResponseDto.of(1, "게시글 조회 성공", PostDetailResponse.from(postWithImages)), HttpStatus.OK);
    }

    @DeleteMapping("/api/s/post/{id}")
    public ResponseEntity<ResponseDto<PostDeleteResponse>> delete(@PathVariable("id") long id, @AuthenticationPrincipal LoginUser loginUser) {
        Long userId = loginUser.getUserId();
        PostDeleteResponse postDeleteResponseDto = postService.delete(id, userId);
        return new ResponseEntity<>(ResponseDto.of(1, "게시글 삭제 성공", postDeleteResponseDto), HttpStatus.OK);
    }

    @PostMapping("/api/s/post/{id}")
    public ResponseEntity<?> react(@PathVariable("id") long id, @RequestParam("type") ReactionType reactionType,
                                   @AuthenticationPrincipal LoginUser loginUser) {
        Long userId = loginUser.getUserId();
        boolean react = reactionService.react(userId, id, reactionType, TargetType.POST);
        postService.react(react, id, reactionType);
        return new ResponseEntity<>(ResponseDto.of(1, "게시글 리액션 반영 성공"), HttpStatus.OK);
    }

    @PatchMapping("/api/s/post/{id}")
    public ResponseEntity<?> modify(@RequestPart(value = "postModify") @Valid PostModifyRequest postModifyRequest, BindingResult bindingResult,
                                    @RequestPart(value = "images", required = false) List<MultipartFile> images, @PathVariable("id") long postId,
                                    @AuthenticationPrincipal LoginUser loginUser) throws IOException {
        Long userId = loginUser.getUserId();
        PostWithImages postWithImages = postService.modify(postId, userId, postModifyRequest, images);
        return new ResponseEntity<>(ResponseDto.of(1, "게시글 수정 성공", postWithImages), HttpStatus.OK);
    }
}
