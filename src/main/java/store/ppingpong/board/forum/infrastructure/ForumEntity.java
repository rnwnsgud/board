package store.ppingpong.board.forum.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.ppingpong.board.forum.domain.Category;
import store.ppingpong.board.forum.domain.Forum;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "forum_tb")
public class ForumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String forumId;
    private String name;
    private String introduction;
    private Category category;
    private Long createdAt;

    @Builder
    private ForumEntity(Long id, String forumId, String name, String introduction, Category category, Long createdAt) {
        this.id = id;
        this.forumId = forumId;
        this.name = name;
        this.introduction = introduction;
        this.category = category;
        this.createdAt = createdAt;
    }

    public static ForumEntity from(Forum forum) {
        return ForumEntity.builder()
                .id(forum.getId())
                .forumId(forum.getForumId())
                .name(forum.getName())
                .introduction(forum.getIntroduction())
                .category(forum.getCategory())
                .createdAt(forum.getCreatedAt())
                .build();
    }

    public Forum toModel() {
        return Forum.builder()
                .id(id)
                .forumId(forumId)
                .name(name)
                .introduction(introduction)
                .category(category)
                .createdAt(createdAt)
                .build();
    }
}
