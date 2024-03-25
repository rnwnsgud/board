package store.ppingpong.board.post.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.domain.PostType;

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
    @Enumerated
    private PostType postType;
    private Long userId;
    private String forumId;
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private PostEntity(String title, String content, PostType postType, Long userId, String forumId, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.title = title;
        this.content = content;
        this.postType = postType;
        this.userId = userId;
        this.forumId = forumId;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static PostEntity from(Post post) {
        return PostEntity.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .postType(post.getPostType())
                .userId(post.getUserId())
                .forumId(post.getForumId())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .build();
    }

    public Post toModel() {
        return Post.from(this);
    }
}
