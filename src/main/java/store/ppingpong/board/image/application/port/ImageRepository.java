package store.ppingpong.board.image.application.port;

import store.ppingpong.board.image.domain.Image;

import java.util.List;

public interface ImageRepository {
    List<Image> saveList(List<Image> images);
    List<Image> findByPostId(long postId);

    int delete(long postId);
}
