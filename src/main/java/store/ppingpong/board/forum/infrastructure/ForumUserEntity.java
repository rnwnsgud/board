package store.ppingpong.board.forum.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.ppingpong.board.forum.domain.ForumUser;
import store.ppingpong.board.forum.domain.ForumUserLevel;
import store.ppingpong.board.user.infrastructure.UserEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = {
        @Index(name = "idx_FU_forum_id", columnList = "forum_id"),
        @Index(name = "idx_FU_user_id", columnList = "user_id")
})
public class ForumUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "forum_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ForumEntity forumEntity;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity userEntity;
    private ForumUserLevel forumUserLevel;

    @Builder
    private ForumUserEntity(Long id, ForumEntity forumEntity, UserEntity userEntity, ForumUserLevel forumUserLevel) {
        this.id = id;
        this.forumEntity = forumEntity;
        this.userEntity = userEntity;
        this.forumUserLevel = forumUserLevel;
    }

    public static ForumUserEntity from(ForumUser forumUser) {
        return ForumUserEntity.builder()
                .id(forumUser.getId())
                .forumEntity(ForumEntity.from(forumUser.getForum()))
                .userEntity(UserEntity.from(forumUser.getUser()))
                .forumUserLevel(forumUser.getForumUserLevel())
                .build();
    }

    public ForumUser toModel() {
        return ForumUser.builder()
                .id(id)
                .user(userEntity.toModel())
                .forum(forumEntity.toModel())
                .forumUserLevel(forumUserLevel)
                .build();
    }
}
