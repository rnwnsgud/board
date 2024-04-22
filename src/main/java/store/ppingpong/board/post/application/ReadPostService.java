package store.ppingpong.board.post.application;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.ppingpong.board.post.domain.ReadPost;
import store.ppingpong.board.post.application.port.ReadPostRepository;

import java.util.Optional;

@Builder
@RequiredArgsConstructor
@Transactional
@Service
public class ReadPostService {

    private final ReadPostRepository readPostRepository;
    public void firstReadThenCreate(long userId, long postId) {
        Optional<ReadPost> readPostOptional = readPostRepository.get(userId, postId);
        if (readPostOptional.isEmpty()) readPostRepository.create(ReadPost.of(userId, postId));
    }
}
