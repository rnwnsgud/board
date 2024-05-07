package store.ppingpong.board.forum.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.PostType;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostTypeRepositoryImpl implements PostTypeRepository{

    private final PostTypeJpaRepository postTypeJpaRepository;
    @Override
    public List<PostType> findByNameInAndCreate(String[] postTypeNames, Forum forum) {
        List<PostTypeEntity> existingPostTypes = postTypeJpaRepository.findByNameInAndCreate(postTypeNames);
        List<PostTypeEntity> nonExistingPostTypes = Arrays.stream(postTypeNames)
                .filter(name -> existingPostTypes.stream().noneMatch(type -> type.getName().equals(name)))
                .map(PostTypeEntity::new)
                .toList();
        postTypeJpaRepository.saveAll(nonExistingPostTypes);
        return nonExistingPostTypes.stream().map(PostType::from).toList();
    }
}
