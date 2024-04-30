package store.ppingpong.board.image.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.ppingpong.board.image.domain.FileExtension;
import store.ppingpong.board.image.domain.Image;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "image_tb")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long postId;
    private String originalName;
    private String storedName;
    private String imgUrl;
    @Enumerated(value = EnumType.STRING)
    private FileExtension fileExtension;

    @Builder(access = AccessLevel.PRIVATE)
    private ImageEntity(Long postId, String originalName, String storedName, String imgUrl, FileExtension fileExtension) {
        this.postId = postId;
        this.originalName = originalName;
        this.storedName = storedName;
        this.imgUrl = imgUrl;
        this.fileExtension = fileExtension;
    }

    public static ImageEntity from(Image image) {
        return ImageEntity.builder()
                .postId(image.getPostId())
                .originalName(image.getOriginalName())
                .storedName(image.getStoredName())
                .imgUrl(image.getImgUrl())
                .fileExtension(image.getFileExtension())
                .build();
    }
}
