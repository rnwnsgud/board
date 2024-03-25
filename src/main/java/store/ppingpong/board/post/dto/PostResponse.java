package store.ppingpong.board.post.dto;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.domain.PostType;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {
    private final String title;
    private final String content;
    private final PostType postType;
    private final Long userId;
    private final String forumId;
    private final LocalDateTime createdAt;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .postType(post.getPostType())
                .userId(post.getUserId())
                .forumId(post.getForumId())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
