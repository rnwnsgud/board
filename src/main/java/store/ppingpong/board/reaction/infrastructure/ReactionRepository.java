package store.ppingpong.board.reaction.infrastructure;

import store.ppingpong.board.reaction.domain.Reaction;

public interface ReactionRepository {
    void create(Reaction reaction);
}
