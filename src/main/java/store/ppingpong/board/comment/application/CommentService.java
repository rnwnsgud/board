package store.ppingpong.board.comment.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.comment.domain.Comment;
import store.ppingpong.board.comment.dto.CommentCreateRequest;
import store.ppingpong.board.comment.infrastructure.CommentEntity;
import store.ppingpong.board.comment.infrastructure.CommentRepository;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment create(Long userId, Long postId, CommentCreateRequest commentCreateRequest) {
        Comment comment = commentCreateRequest.toEntity(userId, postId);
        return commentRepository.create(comment);
    }
}
