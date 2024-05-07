package store.ppingpong.board.post.domain;


import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.common.domain.ClockLocalHolder;
import store.ppingpong.board.common.handler.exception.ResourceNotOwnerException;
import store.ppingpong.board.forum.domain.PostType;
import store.ppingpong.board.post.dto.PostCreate;
import store.ppingpong.board.post.infrastructure.PostEntity;

import java.time.LocalDateTime;
@Getter
public class Post {
    private final Long id;
    private final String title;
    private final String content;
    private final Long postTypeId;
    private final Long userId;
    private final String forumId;
    private final long visitCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastModifiedAt;

    @Builder
    private Post(Long id, String title, String content, Long postTypeId, Long userId, String forumId, long visitCount, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postTypeId = postTypeId;
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
                .postTypeId(postCreate.getPostTypeId())
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
                .postTypeId(postEntity.getPostTypeId())
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
                .postTypeId(postTypeId)
                .userId(userId)
                .forumId(forumId)
                .visitCount(cnt)
                .createdAt(createdAt)
                .lastModifiedAt(lastModifiedAt)
                .build();
    }

    public void checkPostOwner(Long userId) {
        if (this.userId.longValue() != userId.longValue()) throw new ResourceNotOwnerException("Post", userId);
    }
}