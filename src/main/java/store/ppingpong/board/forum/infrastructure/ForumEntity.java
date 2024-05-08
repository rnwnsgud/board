package store.ppingpong.board.forum.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import store.ppingpong.board.forum.domain.Category;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.ForumStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "forum_tb")
public class ForumEntity implements Persistable<String> {

    @Id
    private String forumId;
    private String name;
    private String introduction;
    @Enumerated(value = EnumType.STRING)
    private Category category;
    @Enumerated(value = EnumType.STRING)
    private ForumStatus forumStatus;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTypeEntity> postTypes = new ArrayList<>();
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private ForumEntity(String forumId, String name, String introduction, Category category, ForumStatus forumStatus, List<PostTypeEntity> postTypes, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.forumId = forumId;
        this.name = name;
        this.introduction = introduction;
        this.category = category;
        this.forumStatus = forumStatus;
        this.postTypes = postTypes != null ? postTypes : new ArrayList<>();
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static ForumEntity create(Forum forum) {
        ForumEntity forumEntity = ForumEntity.builder()
                .forumId(forum.getForumId())
                .name(forum.getName())
                .introduction(forum.getIntroduction())
                .category(forum.getCategory())
                .forumStatus(forum.getForumStatus())
                .createdAt(forum.getCreatedAt())
                .lastModifiedAt(forum.getLastModifiedAt())
                .build();
        forumEntity.addPostTypes(new ArrayList<>());
        return forumEntity;
    }

    public static ForumEntity from(Forum forum) {

        return ForumEntity.builder()
                .forumId(forum.getForumId())
                .name(forum.getName())
                .introduction(forum.getIntroduction())
                .category(forum.getCategory())
                .forumStatus(forum.getForumStatus())
                .createdAt(forum.getCreatedAt())
                .lastModifiedAt(forum.getLastModifiedAt())
                .build();
    }

    public Forum toModel() {
        return Forum.from(this);
    }

    public void addPostType(PostTypeEntity postTypeEntity) {
        this.postTypes.add(postTypeEntity);
    }

    public void addPostTypes(List<PostTypeEntity> postTypeEntities) {
        this.postTypes.addAll(postTypeEntities);
    }


    @Override
    public String getId() {
        return forumId;
    }

    @Override
    public boolean isNew() {
        return createdAt == null;
    }

}
