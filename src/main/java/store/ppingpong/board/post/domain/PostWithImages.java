package store.ppingpong.board.post.domain;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.image.domain.Image;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostWithImages {
    private final Long postId;
    private final String title;
    private final String content;
    private final List<Image> images;
    private final Long postTypeId;
    private final Long userId;
    private final String forumId;
    private final Long visitCount;
    private final LocalDateTime createdAt;

    @Builder
    private PostWithImages(Long postId, String title, String content, List<Image> images, Long postTypeId, Long userId, String forumId, Long visitCount, LocalDateTime createdAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.images = images;
        this.postTypeId = postTypeId;
        this.userId = userId;
        this.forumId = forumId;
        this.visitCount = visitCount;
        this.createdAt = createdAt;
    }

    public static PostWithImages of(Post post, List<Image> images) {
        return PostWithImages.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .images(images)
                .postTypeId(post.getPostTypeId())
                .userId(post.getUserId())
                .forumId(post.getForumId())
                .forumId(post.getForumId())
                .visitCount(post.getVisitCount())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
