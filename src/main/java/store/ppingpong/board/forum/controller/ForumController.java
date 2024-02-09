package store.ppingpong.board.forum.controller;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.forum.controller.port.ForumService;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.dto.ForumCreate;
import store.ppingpong.board.forum.dto.ForumListResponse;
import store.ppingpong.board.forum.dto.ForumResponse;

import java.util.List;

@RequestMapping("/api/forums")
@RequiredArgsConstructor
@Builder
@RestController
public class ForumController {

    private final ForumService forumService;

    @PostMapping
    public ResponseEntity<ForumResponse> create(@RequestBody @Valid ForumCreate forumCreate, @AuthenticationPrincipal LoginUser loginUser) {

        Forum forum = forumService.create(forumCreate, loginUser.getUser());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ForumResponse.from(forum));
    }

    // ACTIVE 상태인 Forum만 가져오기
    public ResponseEntity<ForumListResponse> getActiveList() {
        List<Forum> forums = forumService.getActiveList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ForumListResponse(forums));
    }
}
