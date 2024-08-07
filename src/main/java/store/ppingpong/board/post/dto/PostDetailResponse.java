package store.ppingpong.board.post.dto;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.image.domain.Image;
import store.ppingpong.board.post.domain.PostWithImages;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class PostDetailResponse {

    private long id;
    private String title;
    private String content;
    private List<String> imageUrls;
    private Long postTypeId;
    private long userId;
    private String forumId;
    private long visitCount;
    private int likeCount;
    private int dislikeCount;
    private LocalDateTime createdAt;

    public static PostDetailResponse from(PostWithImages postWithImages) {
        return PostDetailResponse.builder()
                .id(postWithImages.getPostId())
                .title(postWithImages.getTitle())
                .content(postWithImages.getContent())
                .imageUrls(postWithImages.getImages().stream().map(Image::getImageUrl).collect(Collectors.toList()))
                .postTypeId(postWithImages.getPostTypeId())
                .userId(postWithImages.getUserId())
                .forumId(postWithImages.getForumId())
                .visitCount(postWithImages.getVisitCount())
                .likeCount(postWithImages.getLikeCount())
                .dislikeCount(postWithImages.getDislikeCount())
                .createdAt(postWithImages.getCreatedAt())
                .build();

    }


}
