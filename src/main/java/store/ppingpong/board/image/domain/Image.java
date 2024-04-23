package store.ppingpong.board.image.domain;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.image.infrastructure.ImageEntity;

@Getter
public class Image {
    private final Long id;
    private final Long postId;
    private final String originalName;
    private final String storedName;
    private final String imgUrl;
    private final FileExtension fileExtension;

    @Builder
    private Image(Long id, Long postId, String originalName, String storedName, String imgUrl, FileExtension fileExtension) {
        this.id = id;
        this.postId = postId;
        this.originalName = originalName;
        this.storedName = storedName;
        this.imgUrl = imgUrl;
        this.fileExtension = fileExtension;
    }

    public static Image from(ImageEntity imageEntity) {
        return Image.builder()
                .id(imageEntity.getId())
                .postId(imageEntity.getPostId())
                .originalName(imageEntity.getOriginalName())
                .storedName(imageEntity.getStoredName())
                .imgUrl(imageEntity.getImgUrl())
                .fileExtension(imageEntity.getFileExtension())
                .build();
    }

    public static Image of(Long postId, String originalName, String storedName, String imgUrl, FileExtension fileExtension) {
        return Image.builder()
                .postId(postId)
                .originalName(originalName)
                .storedName(storedName)
                .imgUrl(imgUrl)
                .fileExtension(fileExtension)
                .build();
    }
}