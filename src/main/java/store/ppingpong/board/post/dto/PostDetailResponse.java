package store.ppingpong.board.post.dto;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.domain.PostType;

import java.time.LocalDateTime;

@Builder
@Getter
public class PostDetailResponse {

    private long id;
    private String title;
    private String content;
    private PostType postType;
    private long userId;
    private String forumId;
    private long visitCount;
    private LocalDateTime createdAt;

    public static PostDetailResponse from(Post post) {
        return PostDetailResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .postType(post.getPostType())
                .userId(post.getUserId())
                .forumId(post.getForumId())
                .visitCount(post.getVisitCount())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
