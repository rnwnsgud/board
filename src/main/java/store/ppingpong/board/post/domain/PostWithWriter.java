package store.ppingpong.board.post.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostWithWriter {
    private final long id;
    private final String title;
    private final long postTypeId;
    private final String writer;
    private final LocalDateTime createdAt;
    private final boolean visited;

    public PostWithWriter(long id, String title, long postTypeId, String writer, LocalDateTime createdAt, boolean visited) {
        this.id = id;
        this.title = title;
        this.postTypeId = postTypeId;
        this.writer = writer;
        this.createdAt = createdAt;
        this.visited = visited;
    }
}
