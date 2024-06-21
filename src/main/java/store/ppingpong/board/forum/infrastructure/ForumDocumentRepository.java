package store.ppingpong.board.forum.infrastructure;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ForumDocumentRepository extends ElasticsearchRepository<ForumDocument, Long> {
    List<ForumDocument> findByNameContainsIgnoreCase(@Param("name") String name);
}
