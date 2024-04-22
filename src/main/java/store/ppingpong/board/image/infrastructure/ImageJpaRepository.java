package store.ppingpong.board.image.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import store.ppingpong.board.image.domain.Image;

public interface ImageJpaRepository extends JpaRepository<ImageEntity, Long> {
}
