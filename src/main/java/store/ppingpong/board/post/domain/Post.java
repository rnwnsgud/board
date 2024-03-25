package store.ppingpong.board.post.domain;


import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.common.service.port.ClockLocalHolder;
import store.ppingpong.board.post.dto.PostCreate;
import store.ppingpong.board.post.infrastructure.PostEntity;

import java.time.LocalDateTime;
@Getter
public class Post {
    private final Long id;
    private final String title;
    private final String content;
    private final PostType postType;
    private final Long userId;
    private final String forumId;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastModifiedAt;

    @Builder
    private Post(Long id, String title, String content, PostType postType, Long userId, String forumId, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postType = postType;
        this.userId = userId;
        this.forumId = forumId;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }
    public static Post of(PostCreate postCreate, Long userId, String forumId, ClockLocalHolder clockLocalHolder) {
        return Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .postType(postCreate.getPostType())
                .userId(userId)
                .forumId(forumId)
                .createdAt(clockLocalHolder.localMills())
                .build();
    }

    public static Post from(PostEntity postEntity) {
        return Post.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .postType(postEntity.getPostType())
                .userId(postEntity.getUserId())
                .forumId(postEntity.getForumId())
                .createdAt(postEntity.getCreatedAt())
                .build();
    }
}