package store.ppingpong.board.comment.dto;

import lombok.Getter;
import store.ppingpong.board.comment.domain.Comment;
import store.ppingpong.board.comment.domain.CommentType;
import store.ppingpong.board.common.EntityStatus;
import store.ppingpong.board.common.handler.exception.comment.CommentDepthException;

@Getter
public class CommentCreateRequest {

    private String text;
    private CommentType commentType;
    private int depth;
    private Long parentId;

    public Comment toEntity(Long userId, Long postId) {
        if (depth == 0 && (parentId != null || commentType != CommentType.POST)) {
            throw new CommentDepthException("댓글의 깊이가 0이지만, 부모댓글이 존재합니다.");
        }
        return Comment.builder()
                .userId(userId)
                .postId(postId)
                .commentType(commentType)
                .text(text)
                .depth(depth)
                .parentId(parentId)
                .entityStatus(EntityStatus.ENABLED)
                .build();

    }
}
