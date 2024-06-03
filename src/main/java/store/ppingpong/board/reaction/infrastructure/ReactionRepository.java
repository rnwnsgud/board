package store.ppingpong.board.reaction.infrastructure;

import store.ppingpong.board.reaction.domain.Reaction;
import store.ppingpong.board.reaction.domain.TargetType;

import java.util.List;

public interface ReactionRepository {
    boolean react(Reaction reaction);
    List<Reaction> findByTargetTypeAndId(TargetType targetType, long id);
}
