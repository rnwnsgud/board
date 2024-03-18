package store.ppingpong.board.forum.controller;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import store.ppingpong.board.common.ResponseDto;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.domain.ForumManagerLevel;
import store.ppingpong.board.forum.dto.ForumAssistantResponse;
import store.ppingpong.board.forum.service.ForumManagerService;
import store.ppingpong.board.forum.service.ForumService;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.service.port.UserRepository;


@RequestMapping("/api/forums/management")
@RequiredArgsConstructor
@Builder
@RestController
public class ForumManagerController {

    private final ForumManagerService forumManagerService;
    private final UserRepository userRepository;

    @GetMapping("/{forumId}/appointment/{userId}")
    public ResponseEntity<ResponseDto<ForumAssistantResponse>> appointmentAssistant(@PathVariable("forumId") String forumId, @PathVariable("userId") Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        checkManager(forumId, loginUser);
        ForumManager assistant = ForumManager.of(forumId, userId, ForumManagerLevel.ASSISTANT);
        forumManagerService.create(assistant);
        User user = userRepository.getById(userId);
        return new ResponseEntity<>(ResponseDto.of(1, "Assistant 임명 성공", ForumAssistantResponse.of(forumId, user)), HttpStatus.OK);
    }

    private void checkManager(String forumId, LoginUser loginUser) {
        ForumManager forumManager = forumManagerService.findManagerByForumId(forumId);
        forumManager.isOwnerOfForum(loginUser.getUser());
    }

    @DeleteMapping("/{forumId}/appointment/{userId}")
    public ResponseEntity<?> removeAssistant(@PathVariable("forumId") String forumId, @PathVariable("userId") Long userId) {
        forumManagerService.delete(forumId, userId);
        return new ResponseEntity<>(ResponseDto.of(1,"Assistant 해임 성공"), HttpStatus.OK);
    }

}
