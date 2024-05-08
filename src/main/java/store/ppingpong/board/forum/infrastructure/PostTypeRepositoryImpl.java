package store.ppingpong.board.forum.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.forum.domain.Forum;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostTypeRepositoryImpl implements PostTypeRepository{

    private final PostTypeJpaRepository postTypeJpaRepository;
    @Override
    public List<PostTypeEntity> findByNameInAndCreate(String[] postTypeNames, Forum forum) {
        List<PostTypeEntity> existingPostTypes = postTypeJpaRepository.findByNameIn(postTypeNames);
        List<PostTypeEntity> nonExistingPostTypes = Arrays.stream(postTypeNames)
                .filter(name -> existingPostTypes.stream().noneMatch(type -> type.getName().equals(name)))
                .map(PostTypeEntity::new)
                .toList();
        return postTypeJpaRepository.saveAll(nonExistingPostTypes);
    }

    @Override
    public void save(PostTypeEntity postTypeEntity) {
        postTypeJpaRepository.save(postTypeEntity);
    }


}
