package store.ppingpong.board.reaction.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.ppingpong.board.reaction.domain.Reaction;
import store.ppingpong.board.reaction.domain.TargetType;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ReactionRepositoryImpl implements ReactionRepository{

    private final ReactionJpaRepository reactionJpaRepository;

    @Override
    public boolean react(Reaction reaction) {
        boolean exist = reactionJpaRepository.existsByUserIdAndTargetIdAndTargetTypeAndReactionType(reaction.getUserId(), reaction.getTargetId(), reaction.getTargetType(), reaction.getReactionType());
        if (!exist) reactionJpaRepository.save(ReactionEntity.from(reaction));
        else reactionJpaRepository.delete(ReactionEntity.from(reaction));
        return exist;
    }

    @Override
    public List<Reaction> findByTargetTypeAndId(TargetType targetType, long id) {
        List<ReactionEntity> reactionEntities = reactionJpaRepository.findByTargetTypeAndTargetId(targetType, id);
        return reactionEntities.stream().map(Reaction::from).toList();
    }

}
