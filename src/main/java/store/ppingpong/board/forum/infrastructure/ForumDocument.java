package store.ppingpong.board.forum.infrastructure;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import store.ppingpong.board.forum.domain.Forum;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Document(indexName = "forums")
public class ForumDocument {
    @Id
    private final String id;
    private final String name;

    public static ForumDocument from(Forum forum) {
        return ForumDocument.builder()
                .id(forum.getForumId())
                .name(forum.getName())
                .build();
    }
}
