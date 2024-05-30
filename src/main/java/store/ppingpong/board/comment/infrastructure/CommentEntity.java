package store.ppingpong.board.comment.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.ppingpong.board.comment.domain.Comment;
import store.ppingpong.board.comment.domain.CommentType;
import store.ppingpong.board.common.BaseEntity;
import store.ppingpong.board.common.EntityStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comment_tb")
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private int depth;

    private CommentType commentType;
    private Long parentId;
    private Long userId;
    private Long postId;

    @Builder(access = AccessLevel.PRIVATE)
    private CommentEntity(String text, int depth, CommentType commentType, Long parentId, Long userId, Long postId, EntityStatus entityStatus) {
        this.text = text;
        this.depth = depth;
        this.commentType = commentType;
        this.parentId = parentId;
        this.userId = userId;
        this.postId = postId;
        this.setEntityStatusWhenCreate(entityStatus);
    }

    public static CommentEntity from(Comment comment) {
        return CommentEntity.builder()
                .text(comment.getText())
                .depth(comment.getDepth())
                .commentType(comment.getCommentType())
                .parentId(comment.getParentId())
                .userId(comment.getUserId())
                .postId(comment.getPostId())
                .build();
    }

    public Comment toDomain() {
        return Comment.from(this);
    }
}
