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
    private String imageUrl;
    @Enumerated(value = EnumType.STRING)
    private FileExtension fileExtension;

    @Builder(access = AccessLevel.PRIVATE)
    private ImageEntity(Long postId, String originalName, String storedName, String imageUrl, FileExtension fileExtension) {
        this.postId = postId;
        this.originalName = originalName;
        this.storedName = storedName;
        this.imageUrl = imageUrl;
        this.fileExtension = fileExtension;
    }

    public static ImageEntity from(Image image) {
        return ImageEntity.builder()
                .postId(image.getPostId())
                .originalName(image.getOriginalName())
                .storedName(image.getStoredName())
                .imageUrl(image.getImageUrl())
                .fileExtension(image.getFileExtension())
                .build();
    }
}
