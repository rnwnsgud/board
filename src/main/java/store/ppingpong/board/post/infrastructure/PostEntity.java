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
    private long visitCount;
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private PostEntity(String title, String content, Long postTypeId, Long userId, String forumId, long visitCount, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.title = title;
        this.content = content;
        this.postTypeId = postTypeId;
        this.userId = userId;
        this.forumId = forumId;
        this.visitCount = visitCount;
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
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .build();
    }

    public Post toModel() {
        return Post.from(this);
    }
}
