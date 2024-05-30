package store.ppingpong.board.comment.domain;

/* https://easthshin.tistory.com/17
댓글에 대댓글을 작성할 수 있다.
댓글이 삭제 되더라도 대댓글은 남아 있어야 한다.
댓글이 삭제된 상태에서, 그 댓글에 달린 대댓글이 모두 삭제되면 댓글 또한 완전히 삭제되어야 한다.
익명으로 작성하는 댓글의 경우 임의의 닉네임(ex: 익명1)으로 보여주고, 한 번 댓글을 작성해 익명 닉네임을 할당 받은 경우 한 게시글에서는 같은 익명 닉네임을 사용하게 되는데,
이를 대댓글에도 적용한다.
*/

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.comment.infrastructure.CommentEntity;
import store.ppingpong.board.common.EntityStatus;

@Getter
public class Comment {

    private final Long id;
    private final String text;
    private final int depth;
    private final CommentType commentType;
    private final Long parentId;
    private final Long userId;
    private final Long postId;
    private final EntityStatus entityStatus;

    @Builder
    private Comment(Long id, String text, int depth, CommentType commentType, Long parentId, Long userId, Long postId, EntityStatus entityStatus) {
        this.id = id;
        this.text = text;
        this.depth = depth;
        this.commentType = commentType;
        this.parentId = parentId;
        this.userId = userId;
        this.postId = postId;
        this.entityStatus = entityStatus;
    }

    public static Comment from(CommentEntity commentEntity) {
        return Comment.builder()
                .id(commentEntity.getId())
                .text(commentEntity.getText())
                .depth(commentEntity.getDepth())
                .commentType(commentEntity.getCommentType())
                .parentId(commentEntity.getParentId())
                .userId(commentEntity.getUserId())
                .postId(commentEntity.getPostId())
                .entityStatus(commentEntity.getEntityStatus())
                .build();
    }
}
