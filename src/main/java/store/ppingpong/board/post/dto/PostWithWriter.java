package store.ppingpong.board.post.dto;

import lombok.Getter;
import store.ppingpong.board.post.domain.PostType;

import java.time.LocalDateTime;

@Getter
public class PostWithWriter {
    private final long id;
    private final String title;
    private final PostType postType;
    private final String writer;
    private final LocalDateTime createdAt;

    public PostWithWriter(long id, String title, PostType postType, String writer, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.postType = postType;
        this.writer = writer;
        this.createdAt = createdAt;
    }
}
