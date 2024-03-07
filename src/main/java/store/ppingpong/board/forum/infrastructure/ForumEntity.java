package store.ppingpong.board.forum.infrastructure;

import ch.qos.logback.core.spi.LifeCycle;
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


@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @CreatedDate
    private LocalDateTime createdAt;


    @Builder(access = AccessLevel.PRIVATE)
    private ForumEntity(String forumId, String name, String introduction, Category category, ForumStatus forumStatus) {
        this.forumId = forumId;
        this.name = name;
        this.introduction = introduction;
        this.category = category;
        this.forumStatus = forumStatus;
    }

    public static ForumEntity from(Forum forum) {
        return ForumEntity.builder()
                .forumId(forum.getForumId())
                .name(forum.getName())
                .introduction(forum.getIntroduction())
                .category(forum.getCategory())
                .forumStatus(forum.getForumStatus())
                .build();
    }

    public Forum toModel() {
        return Forum.from(this);
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
