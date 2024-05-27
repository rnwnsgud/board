package store.ppingpong.board.reaction.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.reaction.infrastructure.ReactionEntity;

@Getter
public class Reaction {

    private final long userId;
    private final long targetId;
    private final ReactionType reactionType;
    private final TargetType targetType;

    @Builder(access = AccessLevel.PRIVATE)
    private Reaction(long userId, TargetType targetType, long targetId, ReactionType reactionType) {
        this.userId = userId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.reactionType = reactionType;
    }

    public static Reaction of(long userId, long targetId, ReactionType reactionType, TargetType targetType) {
        return Reaction.builder()
                .userId(userId)
                .targetId(targetId)
                .reactionType(reactionType)
                .targetType(targetType)
                .build();
    }

    public static Reaction from(ReactionEntity reactionEntity) {
        return Reaction.builder()
                .userId(reactionEntity.getUserId())
                .targetId(reactionEntity.getTargetId())
                .reactionType(reactionEntity.getReactionType())
                .targetType(reactionEntity.getTargetType())
                .build();

    }


}
