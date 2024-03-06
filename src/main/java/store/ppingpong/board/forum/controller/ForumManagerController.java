package store.ppingpong.board.forum.controller;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import store.ppingpong.board.common.ResponseDto;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.forum.controller.port.ForumManagerService;
import store.ppingpong.board.forum.controller.port.ForumService;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.domain.ForumManagerLevel;
import store.ppingpong.board.forum.dto.ForumAssistantResponse;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.service.port.UserRepository;

@RequestMapping("/api/forums/management")
@RequiredArgsConstructor
@Builder
@RestController
public class ForumManagerController {

    private final ForumManagerService forumManagerService;
    private final UserRepository userRepository;
    private final ForumService forumService;

    // assistant : 금지어설정 , 유저차단(포스팅제한, 댓글제한), 글삭제, 삭제목록, 차단목록, 개념글기준설정(추천10개), 태그, 연관 포럼, 공지등록, 개념글해제
    // manager : assistant + assistant 임명, 해임, 위임, 포럼정보수정

    // Manager가 Assistant 임명
    @GetMapping("/{forumId}/appointment/{userId}")
    public ResponseEntity<ResponseDto<ForumAssistantResponse>> appointmentAssistant(@PathVariable("forumId") String forumId, @PathVariable("userId") long userId, @AuthenticationPrincipal LoginUser loginUser) {
        checkManager(forumId, loginUser);
        ForumManager assistant = ForumManager.of(forumId, userId, ForumManagerLevel.ASSISTANT);
        forumManagerService.save(assistant);
        User user = userRepository.getById(userId);
        return new ResponseEntity<>(ResponseDto.of(1, "Assistant 임명 완료", ForumAssistantResponse.of(forumId, user)), HttpStatus.OK);
    }

    private void checkManager(String forumId, LoginUser loginUser) {
        ForumManager forumManager = forumManagerService.findByForumId(forumId);
        forumManager.isSameUser(loginUser.getUser());
    }

    // Manager가 Assistant 해임
    @DeleteMapping("/{forumId}/appointment/{userId}")
    public ResponseEntity<?> removeAssistant(@PathVariable("forumId") String forumId, @PathVariable("userId") long userId) {
        forumManagerService.delete(forumId, userId);
        return new ResponseEntity<>(ResponseDto.of(1,"Assistant 해임 완료"), HttpStatus.OK);
    }

}
