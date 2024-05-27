package store.ppingpong.board.forum.controller;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.ppingpong.board.common.ResponseDto;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.common.domain.ClockLocalHolder;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.dto.*;
import store.ppingpong.board.forum.application.ForumManagerService;
import store.ppingpong.board.forum.application.ForumService;
import store.ppingpong.board.post.domain.PostWithWriter;
import store.ppingpong.board.post.application.PostService;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.application.port.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Builder
@RestController
public class ForumController {

    private final ForumService forumService;
    private final ForumManagerService forumManagerService;
    private final PostService postService;
    private final UserRepository userRepository;
    private final ClockLocalHolder clockLocalHolder;

    @PostMapping("/api/s/forums")
    public ResponseEntity<ResponseDto<ForumResponse>> create(@RequestBody @Valid ForumCreate forumCreate, BindingResult bindingResult,
                                                             @AuthenticationPrincipal LoginUser loginUser) {
        User user = loginUser.getUser();
        Forum forum = forumService.create(forumCreate, user);
        return new ResponseEntity<>(ResponseDto.of(1, "포럼의 생성 성공", ForumResponse.from(forum)), HttpStatus.CREATED);
    }

    @GetMapping("/api/forums")
    public ResponseEntity<ResponseDto<ForumListResponse>> getActiveList() {
        List<Forum> forums = forumService.getActiveList();
        return new ResponseEntity<>(ResponseDto.of(1, "ACTIVE 상태인 포럼 리스트 가져오기 성공", ForumListResponse.from(forums)), HttpStatus.OK);
    }

    @GetMapping("/api/forums/{forumId}")
    public ResponseEntity<ResponseDto<ForumDetailResponse>> get(@PathVariable("forumId") String forumId, @RequestParam(value = "listNum", required = false) Integer listNum,
                                                                @RequestParam(value = "search_head", required = false) Long searchHead,@PageableDefault(page = 1) Pageable pageable) {
        Forum forum = forumService.findById(forumId);
        User forumManager = userRepository.findForumManager(forumId);
        Page<PostWithWriter> postWithWriters = postService.getList(forumId, listNum, searchHead, pageable);
        List<User> forumAssistant = userRepository.findForumAssistant(forumId);
        return new ResponseEntity<>(ResponseDto.of(1, "해당 포럼 상세정보 가져오기 성공", ForumDetailResponse.of(forum, forumManager, forumAssistant, postWithWriters)), HttpStatus.OK);
    }
    @PatchMapping("/api/s/forums/{forumId}")
    public ResponseEntity<ResponseDto<ForumUpdateResponse>> modify(@PathVariable("forumId") String forumId, @RequestBody @Valid ForumUpdate forumUpdate,
                                    BindingResult bindingResult, @AuthenticationPrincipal LoginUser loginUser) {
        User authorizedUser = userRepository.findManagerOrAssistant(forumId, loginUser.getUser().getId());
        Forum forum = forumService.modify(forumId, forumUpdate);
        return new ResponseEntity<>(ResponseDto.of(1, "포럼 수정 성공", ForumUpdateResponse.of(forum, authorizedUser, clockLocalHolder)), HttpStatus.OK);
    }


}
