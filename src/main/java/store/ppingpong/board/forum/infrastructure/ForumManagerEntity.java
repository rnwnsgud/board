package store.ppingpong.board.forum.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Builder
    public ForumManagerEntity(Long id, String forumId, Long userId, ForumManagerLevel forumManagerLevel) {
        this.id = id;
        this.forumId = forumId;
        this.userId = userId;
        this.forumManagerLevel = forumManagerLevel;
    }


    public static ForumManagerEntity from(ForumManager forumManager) {
        return ForumManagerEntity.builder()
                .id(forumManager.getId())
                .forumId(forumManager.getForumId())
                .userId(forumManager.getUserId())
                .forumManagerLevel(forumManager.getForumManagerLevel())
                .build();
    }

    public ForumManager toModel() {
        return ForumManager.builder()
                .id(id)
                .userId(userId)
                .forumId(forumId)
                .forumManagerLevel(forumManagerLevel)
                .build();
    }
}
