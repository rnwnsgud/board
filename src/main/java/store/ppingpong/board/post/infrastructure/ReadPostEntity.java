package store.ppingpong.board.post.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import store.ppingpong.board.post.domain.ReadPost;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "readPost_tb")
public class ReadPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long postId;

    @Builder(access = AccessLevel.PRIVATE)
    private ReadPostEntity(Long id, Long userId, Long postId) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
    }

    public static ReadPostEntity from(ReadPost readPost) {
        return ReadPostEntity.builder()
                .id(readPost.getId())
                .userId(readPost.getUserId())
                .postId(readPost.getPostId())
                .build();
    }

    public ReadPost toModel() {
        return ReadPost.from(this);
    }
}
