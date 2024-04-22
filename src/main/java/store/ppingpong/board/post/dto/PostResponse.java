package store.ppingpong.board.post.dto;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.image.domain.Image;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.domain.PostType;
import store.ppingpong.board.post.domain.PostWithImages;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostResponse {
    private final String title;
    private final String content;
    private final List<Image> images;
    private final PostType postType;
    private final Long userId;
    private final String forumId;
    private final LocalDateTime createdAt;

    public static PostResponse from(PostWithImages postWithImages) {
        return PostResponse.builder()
                .title(postWithImages.getTitle())
                .content(postWithImages.getContent())
                .images(postWithImages.getImages())
                .postType(postWithImages.getPostType())
                .userId(postWithImages.getUserId())
                .forumId(postWithImages.getForumId())
                .createdAt(postWithImages.getCreatedAt())
                .build();
    }
}
