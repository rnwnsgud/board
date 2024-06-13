package store.ppingpong.board.post.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import store.ppingpong.board.post.domain.Post;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "post_tb")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Long postTypeId;
    private Long userId;
    private String forumId;
    private int visitCount;
    private int likeCount;
    private int dislikeCount;
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private PostEntity(Long id, String title, String content, Long postTypeId, Long userId, String forumId, int visitCount, int likeCount, int dislikeCount, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postTypeId = postTypeId;
        this.userId = userId;
        this.forumId = forumId;
        this.visitCount = visitCount;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static PostEntity from(Post post) {
        return PostEntity.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .postTypeId(post.getPostTypeId())
                .userId(post.getUserId())
                .forumId(post.getForumId())
                .visitCount(post.getVisitCount())
                .likeCount(post.getLikeCount())
                .dislikeCount(post.getDislikeCount())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .build();
    }

    public static PostEntity modify(Post post) {
        return PostEntity.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .postTypeId(post.getPostTypeId())
                .userId(post.getUserId())
                .forumId(post.getForumId())
                .visitCount(post.getVisitCount())
                .likeCount(post.getLikeCount())
                .dislikeCount(post.getDislikeCount())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .build();
    }

    public Post toModel() {
        return Post.from(this);
    }
}
