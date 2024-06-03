package store.ppingpong.board.reaction.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import store.ppingpong.board.reaction.domain.ReactionType;
import store.ppingpong.board.reaction.domain.TargetType;

import java.util.List;

public interface ReactionJpaRepository extends JpaRepository<ReactionEntity, Long> {
    boolean existsByUserIdAndTargetIdAndTargetTypeAndReactionType(long userId, long targetId, TargetType targetType, ReactionType reactionType);
    List<ReactionEntity> findByTargetTypeAndTargetId(TargetType targetType, long targetId);
}
