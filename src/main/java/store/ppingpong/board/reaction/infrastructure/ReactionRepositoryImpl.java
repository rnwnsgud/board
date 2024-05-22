package store.ppingpong.board.reaction.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.reaction.domain.Reaction;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ReactionRepositoryImpl implements ReactionRepository{

    private final ReactionJpaRepository reactionJpaRepository;

    @Override
    public void create(Reaction reaction) {
        Optional<ReactionEntity> reactionOptional = reactionJpaRepository.findByUserIdAndTargetIdAndTargetTypeAndReactionType(reaction.getUserId(), reaction.getTargetId(), reaction.getTargetType(), reaction.getReactionType());
        if (reactionOptional.isEmpty()) reactionJpaRepository.save(ReactionEntity.from(reaction));
    }
}
