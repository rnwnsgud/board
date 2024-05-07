package store.ppingpong.board.forum.controller;

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
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.domain.ForumManagerLevel;
import store.ppingpong.board.forum.dto.ForumAssistantResponse;
import store.ppingpong.board.forum.dto.ForumPostTypeCreate;
import store.ppingpong.board.forum.dto.ForumPostTypeResponse;
import store.ppingpong.board.forum.service.ForumManagerService;
import store.ppingpong.board.forum.service.ForumService;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.application.port.UserRepository;


@RequestMapping("/api/forums/management")
@RequiredArgsConstructor
@Builder
@RestController
public class ForumManagerController {

    private final ForumManagerService forumManagerService;
    private final ForumService forumService;
    private final UserRepository userRepository;

    @GetMapping("/{forumId}/appointment/{userId}")
    public ResponseEntity<ResponseDto<ForumAssistantResponse>> appointmentAssistant(@PathVariable("forumId") String forumId, @PathVariable("userId") Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        checkManager(forumId, loginUser);
        ForumManager assistant = ForumManager.of(forumId, userId, ForumManagerLevel.ASSISTANT);
        forumManagerService.create(assistant);
        User user = userRepository.getById(userId);
        return new ResponseEntity<>(ResponseDto.of(1, "Assistant 임명 성공", ForumAssistantResponse.of(forumId, user)), HttpStatus.OK);
    }

    @DeleteMapping("/{forumId}/appointment/{userId}")
    public ResponseEntity<?> removeAssistant(@PathVariable("forumId") String forumId, @PathVariable("userId") Long userId) {
        // TODO : 관리자만 해임하게 바꾸기
        forumManagerService.delete(forumId, userId);
        return new ResponseEntity<>(ResponseDto.of(1,"Assistant 해임 성공"), HttpStatus.OK);
    }

    @PostMapping("/{forumId}/post_type")
    public ResponseEntity<ResponseDto<ForumPostTypeResponse>> createPostType(@PathVariable("forumId") String forumId, @RequestBody @Valid ForumPostTypeCreate forumPostTypeCreate,
                                            BindingResult bindingResult, @AuthenticationPrincipal LoginUser loginUser) {
        checkManager(forumId, loginUser);
        Forum forum = forumService.findById(forumId);
        ForumPostTypeResponse forumPostTypeResponse = forumManagerService.createPostType(forumPostTypeCreate.getPostTypes(), forum);
        return new ResponseEntity<>(ResponseDto.of(1, "말머리 등록 성공", forumPostTypeResponse), HttpStatus.OK);
    }

    private void checkManager(String forumId, LoginUser loginUser) {
        ForumManager forumManager = forumManagerService.findManagerByForumId(forumId);
        forumManager.isOwnerOfForum(loginUser.getUser());
    }

}
