package store.ppingpong.board.forum.controller;

import jakarta.validation.Valid;
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
import store.ppingpong.board.forum.dto.ForumCreate;
import store.ppingpong.board.forum.dto.ForumDetailResponse;
import store.ppingpong.board.forum.dto.ForumListResponse;
import store.ppingpong.board.forum.dto.ForumResponse;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.service.port.UserRepository;

import java.util.List;

@RequestMapping("/api/forums")
@RequiredArgsConstructor
@Builder
@RestController
public class ForumController {

    private final ForumService forumService;
    private final ForumManagerService forumManagerService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ResponseDto<ForumResponse>> create(@RequestBody @Valid ForumCreate forumCreate, @AuthenticationPrincipal LoginUser loginUser) {
        Forum forum = forumService.create(forumCreate, loginUser.getUser());
        return new ResponseEntity<>(ResponseDto.of(1,"포럼생성요청",ForumResponse.from(forum)), HttpStatus.CREATED);
    }

    // ACTIVE 상태인 Forum만 가져오기
    @GetMapping
    public ResponseEntity<ResponseDto<ForumListResponse>> getActiveList() {
        List<Forum> forums = forumService.getActiveList();
        return new ResponseEntity<>(ResponseDto.of(1,"ACTIVE 포럼 리스트 가져오기", ForumListResponse.from(forums)), HttpStatus.OK);
    }

    @GetMapping("/{forumId}") // TODO : 포스팅 기능 추가 후 responseDto 변경
    public ResponseEntity<ResponseDto<ForumDetailResponse>> get(@PathVariable("forumId") String forumId) {
        Forum forum = forumService.findById(forumId);
        User forumManager = userRepository.findForumManager(forumId);
        List<User> forumAssistant = userRepository.findForumAssistant(forumId);
        return new ResponseEntity<>(ResponseDto.of(1,"해당 포럼 상세정보 가져오기", ForumDetailResponse.of(forum, forumManager, forumAssistant)), HttpStatus.OK);
    }
}
