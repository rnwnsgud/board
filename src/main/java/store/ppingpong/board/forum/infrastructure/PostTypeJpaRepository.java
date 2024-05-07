package store.ppingpong.board.forum.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTypeJpaRepository extends JpaRepository<PostTypeEntity, Long> {
    List<PostTypeEntity> findByNameInAndCreate(String[] names);
}
