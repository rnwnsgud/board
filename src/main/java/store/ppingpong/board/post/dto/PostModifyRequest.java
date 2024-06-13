package store.ppingpong.board.post.dto;

import lombok.Getter;
import store.ppingpong.board.post.domain.Post;

import java.time.LocalDateTime;

@Getter
public class PostModifyRequest {
    private String title;
    private String content;
    private Long postTypeId;

    public Post toEntity(Post post) {
        return Post.builder()
                .id(post.getId())
                .title(title)
                .content(content)
                .postTypeId(postTypeId)
                .userId(post.getUserId())
                .forumId(post.getForumId())
                .visitCount(post.getVisitCount())
                .likeCount(post.getLikeCount())
                .dislikeCount(post.getDislikeCount())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(LocalDateTime.now())
                .build();
    }
}
