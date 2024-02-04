package store.ppingpong.board.forum.controller;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.ppingpong.board.forum.controller.port.ForumService;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.dto.ForumCreate;
import store.ppingpong.board.forum.dto.ForumResponse;

@RequestMapping("/api/forums")
@RequiredArgsConstructor
@Builder
@RestController
public class ForumCreateController {

    private final ForumService forumService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ForumCreate forumCreate) {
        Forum forum = forumService.create(forumCreate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ForumResponse.from(forum));
    }
}
