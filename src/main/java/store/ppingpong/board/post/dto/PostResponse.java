package store.ppingpong.board.post.dto;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.image.domain.Image;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostResponse {
    private final String title;
    private final String content;
    private final List<Image> images;
    private final Long postType;
    private final Long userId;
    private final String forumId;
    private final LocalDateTime createdAt;

    public static PostResponse from(PostCreateResponse postCreateResponse) {
        return PostResponse.builder()
                .title(postCreateResponse.getTitle())
                .content(postCreateResponse.getContent())
                .images(postCreateResponse.getImages())
                .postType(postCreateResponse.getPostTypeId())
                .userId(postCreateResponse.getUserId())
                .forumId(postCreateResponse.getForumId())
                .createdAt(postCreateResponse.getCreatedAt())
                .build();
    }
}
