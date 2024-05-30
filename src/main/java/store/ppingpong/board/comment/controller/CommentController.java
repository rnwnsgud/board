package store.ppingpong.board.comment.controller;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.ppingpong.board.comment.application.CommentService;
import store.ppingpong.board.comment.domain.Comment;
import store.ppingpong.board.comment.dto.CommentCreateRequest;
import store.ppingpong.board.common.ResponseDto;
import store.ppingpong.board.common.config.auth.LoginUser;

@RequiredArgsConstructor
@Builder
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comment/{postId}")
    public ResponseEntity<?> create(@RequestBody CommentCreateRequest commentCreateRequest, @PathVariable("postId") long postId, @AuthenticationPrincipal LoginUser loginUser) {
        Long userId;
        if (loginUser.getUserId() == null) userId = -1L;
        else userId = loginUser.getUserId();
        commentService.create(userId, postId, commentCreateRequest);
        return new ResponseEntity<>(ResponseDto.of(1, "댓글 생성 성공"), HttpStatus.CREATED);
    }
}
