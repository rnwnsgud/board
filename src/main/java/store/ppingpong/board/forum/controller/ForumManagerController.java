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
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.dto.ForumAssistantResponse;
import store.ppingpong.board.user.controller.port.UserService;
import store.ppingpong.board.user.domain.User;

import java.util.List;

@RequestMapping("/api/forums/management")
@RequiredArgsConstructor
@Builder
@RestController
public class ForumManagerController {

    private final ForumManagerService forumManagerService;
    private final UserService userService;
    private final ForumService forumService;


    // 할꺼 : 성능에 관한 포스팅, e.g. ForumManagerEntity 인덱싱, 스프링캐시(@Cache)

    // assistant : 금지어설정 , 유저차단(포스팅제한, 댓글제한), 글삭제, 삭제목록, 차단목록, 개념글기준설정(추천10개), 태그, 연관 포럼, 공지등록, 개념글해제
    // manager : assistant + assistant 임명, 해임, 위임, 포럼정보수정


    // Manager가 Assistant 임명
    @GetMapping("/{forumId}/appointment/{userId}")
    public ResponseEntity<?> appointmentAssistant(@PathVariable("forumId") String forumId, @PathVariable("userId") long userId, @AuthenticationPrincipal LoginUser loginUser) {
        ForumManager forumManager = forumManagerService.findForumManager(forumId);
        forumManager.isSameUser(loginUser.getUser());

        User user = userService.getById(userId);
        Forum forum = forumService.findById(forumId);
        ForumManager assistant = ForumManager.valueOf(forum, user);
        forumManagerService.save(assistant);

        return new ResponseEntity<>(ResponseDto.of(1, "Assistant 임명 완료", ForumAssistantResponse.of(forumId, user)), HttpStatus.OK);
    }

    // Manager가 Assistant 해임
    @DeleteMapping("/{forumId}/appointment/{userId}")
    public ResponseEntity<?> removeAssistant(@PathVariable("forumId") String forumId) {
        return null;
    }

}
