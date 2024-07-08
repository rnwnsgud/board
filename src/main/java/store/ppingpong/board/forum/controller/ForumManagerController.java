package store.ppingpong.board.forum.controller;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.ppingpong.board.common.ResponseDto;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.domain.ForumManagerLevel;
import store.ppingpong.board.forum.dto.ForumAssistantResponse;
import store.ppingpong.board.forum.dto.ForumPostTypeCreate;
import store.ppingpong.board.forum.dto.ForumPostTypeResponse;
import store.ppingpong.board.forum.application.ForumManagerService;
import store.ppingpong.board.forum.application.ForumService;
import store.ppingpong.board.post.application.PostService;
import store.ppingpong.board.post.dto.PostCreateRequest;
import store.ppingpong.board.post.dto.PostCreateResponse;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.application.port.UserRepository;

import java.io.IOException;
import java.util.List;


@RequestMapping("/api/forums/management")
@RequiredArgsConstructor
@Builder
@RestController
public class ForumManagerController {

    private final ForumManagerService forumManagerService;
    private final ForumService forumService;
    private final UserRepository userRepository;
    private final PostService postService;

    @GetMapping("/{forumId}/appointment/{userId}")
    public ResponseEntity<ResponseDto<ForumAssistantResponse>> appointmentAssistant(@PathVariable("forumId") String forumId, @PathVariable("userId") Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        checkManager(forumId, loginUser);
        ForumManager assistant = ForumManager.of(forumId, userId, ForumManagerLevel.ASSISTANT);
        forumManagerService.create(assistant);
        User user = userRepository.getById(userId);
        return new ResponseEntity<>(ResponseDto.of(1, "Assistant 임명 성공", ForumAssistantResponse.of(forumId, user)), HttpStatus.OK);
    }

    @DeleteMapping("/{forumId}/appointment/{userId}")
    public ResponseEntity<?> removeAssistant(@PathVariable("forumId") String forumId, @PathVariable("userId") Long userId,
                                            @AuthenticationPrincipal LoginUser loginUser) {
        checkManager(forumId, loginUser);
        forumManagerService.delete(forumId, userId);
        return new ResponseEntity<>(ResponseDto.of(1,"Assistant 해임 성공"), HttpStatus.OK);
    }

    @PostMapping("/{forumId}/post_type")
    public ResponseEntity<?> createPostType(@PathVariable("forumId") String forumId, @RequestBody @Valid ForumPostTypeCreate forumPostTypeCreate,
                                            BindingResult bindingResult, @AuthenticationPrincipal LoginUser loginUser) {
        checkManager(forumId, loginUser);
        Forum forum = forumService.getById(forumId);
        ForumPostTypeResponse forumPostTypeResponse = forumManagerService.createPostType(forumPostTypeCreate.getPostTypes(), forum);
        return new ResponseEntity<>(ResponseDto.of(1, "말머리 등록 성공", forumPostTypeResponse), HttpStatus.OK);
    }

    @PostMapping(value = "/{forumId}/announcement" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createNotice(@RequestPart(value = "postCreate") @Valid PostCreateRequest postCreateRequest, BindingResult bindingResult,
                                                @RequestPart(value = "images", required = false) List<MultipartFile> images, @PathVariable("forumId") String forumId,
                                                @AuthenticationPrincipal LoginUser loginUser) throws IOException {
        checkManager(forumId, loginUser);
        if (!postCreateRequest.isNotice()) throw new IllegalArgumentException("공지사항에 필요한 필드값이 잘못되었습니다.");
        forumService.getById(forumId);
        Long userId = loginUser.getUserId();
        PostCreateResponse postCreateResponse = postService.create(postCreateRequest, userId, forumId, images);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.of(1, "공지사항 등록 성공", postCreateResponse));
    }

    private void checkManager(String forumId, LoginUser loginUser) {
        ForumManager forumManager = forumManagerService.findManagerByForumId(forumId);
        forumManager.isOwnerOfForum(loginUser.getUser());
    }

}
