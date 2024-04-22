package store.ppingpong.board.image.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.image.domain.Image;
import store.ppingpong.board.image.application.port.ImageRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ImageRepositoryImpl implements ImageRepository {

    private final ImageJpaRepository imageJpaRepository;
    @Override
    public List<Image> saveList(List<Image> images) {
        List<ImageEntity> imageEntities = images.stream().map(ImageEntity::from).collect(Collectors.toList());
        return imageJpaRepository.saveAll(imageEntities).stream().map(Image::from).collect(Collectors.toList());
    }
}
