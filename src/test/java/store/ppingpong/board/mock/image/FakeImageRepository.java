package store.ppingpong.board.mock.image;

import store.ppingpong.board.image.application.port.ImageRepository;
import store.ppingpong.board.image.domain.Image;

import java.util.ArrayList;
import java.util.List;

public class FakeImageRepository implements ImageRepository {
    private List<Image> images = new ArrayList<>();
    @Override
    public List<Image> saveList(List<Image> images) {
        return null;
    }

    @Override
    public List<Image> findByPostId(long postId) {
        return images;
    }

    @Override
    public int delete(long postId) {
        return 0;
    }
}
