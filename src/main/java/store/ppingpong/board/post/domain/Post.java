package store.ppingpong.board.post.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.common.domain.ClockLocalHolder;
import store.ppingpong.board.common.handler.exception.resource.ResourceNotOwnerException;
import store.ppingpong.board.post.dto.PostCreateRequest;
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

    @Builder(access = AccessLevel.PRIVATE)
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
    public static Post of(PostCreateRequest postCreateRequest, Long userId, String forumId, ClockLocalHolder clockLocalHolder) {
        return Post.builder()
                .title(postCreateRequest.getTitle())
                .content(postCreateRequest.getContent())
                .postTypeId(postCreateRequest.getPostTypeId())
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

    public Post visit() {

        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .postTypeId(postTypeId)
                .userId(userId)
                .forumId(forumId)
                .visitCount(visitCount+1)
                .createdAt(createdAt)
                .lastModifiedAt(lastModifiedAt)
                .build();
    }

    public void checkPostOwner(Long userId) {
        if (this.userId.longValue() != userId.longValue()) throw new ResourceNotOwnerException("Post", userId);
    }
}