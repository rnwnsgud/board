package store.ppingpong.board.post.domain;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.post.infrastructure.ReadPostEntity;

@Getter
public class ReadPost {
    private final Long id;
    private final Long userId;
    private final Long postId;

    @Builder
    private ReadPost(Long id, Long userId, Long postId) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
    }

    public static ReadPost from(ReadPostEntity readPostEntity) {
        return ReadPost.builder()
                .id(readPostEntity.getId())
                .userId(readPostEntity.getUserId())
                .postId(readPostEntity.getPostId())
                .build();
    }

    public static ReadPost of(long userId, long postId) {
        return ReadPost.builder()
                .userId(userId)
                .postId(postId)
                .build();
    }
}
