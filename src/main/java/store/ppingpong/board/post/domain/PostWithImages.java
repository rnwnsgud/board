package store.ppingpong.board.post.domain;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.image.domain.Image;
import store.ppingpong.board.reaction.domain.Reaction;
import store.ppingpong.board.reaction.domain.ReactionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostWithImages {
    private final Long postId;
    private final String title;
    private final String content;
    private final List<Image> images;
    private final Long postTypeId;
    private final Long userId;
    private final String forumId;
    private final int visitCount;
    private final int likeCount;
    private final int dislikeCount;
    private final LocalDateTime createdAt;

    @Builder
    private PostWithImages(Long postId, String title, String content, List<Image> images, Long postTypeId, Long userId, String forumId, int visitCount, int likeCount, int dislikeCount, LocalDateTime createdAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.images = images;
        this.postTypeId = postTypeId;
        this.userId = userId;
        this.forumId = forumId;
        this.visitCount = visitCount;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.createdAt = createdAt;
    }

    public static PostWithImages of(Post post, List<Image> images, List<Reaction> reactions) {
        int likeCount = reactions.stream().filter(reaction -> reaction.getReactionType().equals(ReactionType.LIKE)).toList().size();
        int dislikeCount = reactions.stream().filter(reaction -> reaction.getReactionType().equals(ReactionType.DISLIKE)).toList().size();

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
                .likeCount(likeCount)
                .dislikeCount(dislikeCount)
                .createdAt(post.getCreatedAt())
                .build();
    }

    public static PostWithImages modify(Post post, List<Image> images) {
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
                .likeCount(post.getLikeCount())
                .dislikeCount(post.getDislikeCount())
                .createdAt(post.getCreatedAt())
                .build();
    }

}
