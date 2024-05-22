package store.ppingpong.board.reaction.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.ppingpong.board.reaction.domain.Reaction;
import store.ppingpong.board.reaction.domain.ReactionType;
import store.ppingpong.board.reaction.domain.TargetType;
import store.ppingpong.board.reaction.infrastructure.ReactionRepository;

@RequiredArgsConstructor
@Service
public class ReactionService {

    private final ReactionRepository reactionRepository;

    public void create(Long userId, long targetId, ReactionType reactionType, TargetType targetType) {
        Reaction reaction = Reaction.of(userId, targetId, reactionType, targetType);
        reactionRepository.create(reaction);
    }
}
