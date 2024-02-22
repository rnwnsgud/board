package store.ppingpong.board.forum.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.domain.ForumManagerLevel;
import store.ppingpong.board.user.infrastructure.UserEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = {
        @Index(name = "idx_FU_forum_id", columnList = "forum_id"),
        @Index(name = "idx_FU_user_id", columnList = "user_id")
})
public class ForumManagerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "forum_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ForumEntity forumEntity;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity userEntity;

    @Enumerated(value = EnumType.STRING)
    private ForumManagerLevel forumManagerLevel;

    @Builder
    private ForumManagerEntity(Long id, ForumEntity forumEntity, UserEntity userEntity, ForumManagerLevel forumManagerLevel) {
        this.id = id;
        this.forumEntity = forumEntity;
        this.userEntity = userEntity;
        this.forumManagerLevel = forumManagerLevel;
    }

    public static ForumManagerEntity from(ForumManager forumUser) {
        return ForumManagerEntity.builder()
                .id(forumUser.getId())
                .forumEntity(ForumEntity.from(forumUser.getForum()))
                .userEntity(UserEntity.from(forumUser.getUser()))
                .forumManagerLevel(forumUser.getForumUserLevel())
                .build();
    }

    public ForumManager toModel() {
        return ForumManager.builder()
                .id(id)
                .user(userEntity.toModel())
                .forum(forumEntity.toModel())
                .forumUserLevel(forumManagerLevel)
                .build();
    }
}
