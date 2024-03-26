package store.ppingpong.board.forum.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.ppingpong.board.forum.domain.ForumAccessStatus;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.domain.ForumManagerLevel;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ForumManagerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String forumId;
    private Long userId;
    @Enumerated(value = EnumType.STRING)
    private ForumManagerLevel forumManagerLevel;
    @Enumerated(value = EnumType.STRING)
    private ForumAccessStatus forumAccessStatus;

    @Builder(access = AccessLevel.PRIVATE)
    private ForumManagerEntity(String forumId, Long userId, ForumManagerLevel forumManagerLevel, ForumAccessStatus forumAccessStatus) {
        this.forumId = forumId;
        this.userId = userId;
        this.forumManagerLevel = forumManagerLevel;
        this.forumAccessStatus = forumAccessStatus;
    }

    public static ForumManagerEntity from(ForumManager forumManager) {
        return ForumManagerEntity.builder()
                .forumId(forumManager.getForumId())
                .userId(forumManager.getUserId())
                .forumManagerLevel(forumManager.getForumManagerLevel())
                .forumAccessStatus(forumManager.getForumAccessStatus())
                .build();
    }

    public ForumManager toModel() {
        return ForumManager.from(this);
    }
}
