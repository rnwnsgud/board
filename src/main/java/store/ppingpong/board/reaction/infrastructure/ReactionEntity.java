package store.ppingpong.board.reaction.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.ppingpong.board.common.BaseEntity;
import store.ppingpong.board.common.EntityStatus;
import store.ppingpong.board.reaction.domain.Reaction;
import store.ppingpong.board.reaction.domain.ReactionType;
import store.ppingpong.board.reaction.domain.TargetType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "reaction_tb")
public class ReactionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long targetId;
    @Enumerated(EnumType.STRING)
    private TargetType targetType;
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

    @Builder(access = AccessLevel.PRIVATE)
    private ReactionEntity(Long userId, Long targetId, TargetType targetType, EntityStatus entityStatus, ReactionType reactionType) {
        this.userId = userId;
        this.targetId = targetId;
        this.targetType = targetType;
        this.reactionType = reactionType;
        this.setEntityStatusWhenCreate(entityStatus);
    }

    public static ReactionEntity from(Reaction reaction) {
        return ReactionEntity.builder()
                .userId(reaction.getUserId())
                .targetId(reaction.getTargetId())
                .targetType(reaction.getTargetType())
                .reactionType(reaction.getReactionType())
                .entityStatus(EntityStatus.ENABLED)
                .build();

    }
}
