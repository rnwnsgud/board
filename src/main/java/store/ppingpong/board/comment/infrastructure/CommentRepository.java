package store.ppingpong.board.comment.infrastructure;

import store.ppingpong.board.comment.domain.Comment;

public interface CommentRepository {
    Comment create(Comment comment);
}
