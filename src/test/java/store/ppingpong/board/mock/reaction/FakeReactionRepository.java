package store.ppingpong.board.mock.reaction;

import store.ppingpong.board.reaction.domain.Reaction;
import store.ppingpong.board.reaction.domain.TargetType;
import store.ppingpong.board.reaction.infrastructure.ReactionRepository;

import java.util.ArrayList;
import java.util.List;

public class FakeReactionRepository implements ReactionRepository {
    @Override
    public void create(Reaction reaction) {

    }

    @Override
    public List<Reaction> findByTargetTypeAndId(TargetType targetType, long id) {
        return new ArrayList<>();
    }
}
