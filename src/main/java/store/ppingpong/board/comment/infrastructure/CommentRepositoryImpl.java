package store.ppingpong.board.comment.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.comment.domain.Comment;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentRepository{

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment create(Comment comment) {
        return commentJpaRepository.save(CommentEntity.from(comment)).toDomain();
    }
}
