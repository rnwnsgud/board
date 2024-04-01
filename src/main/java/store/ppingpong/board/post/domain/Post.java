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
    private final long visitCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastModifiedAt;

    @Builder
    private Post(Long id, String title, String content, PostType postType, Long userId, String forumId, long visitCount, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postType = postType;
        this.userId = userId;
        this.forumId = forumId;
        this.visitCount = visitCount;
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
                .visitCount(postEntity.getVisitCount())
                .createdAt(postEntity.getCreatedAt())
                .lastModifiedAt(postEntity.getLastModifiedAt())
                .build();
    }

    public Post visit(long userId) {
        long cnt = visitCount;
        if (this.userId != userId) cnt ++;
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .postType(postType)
                .userId(userId)
                .forumId(forumId)
                .visitCount(cnt)
                .createdAt(createdAt)
                .lastModifiedAt(lastModifiedAt)
                .build();
    }
}