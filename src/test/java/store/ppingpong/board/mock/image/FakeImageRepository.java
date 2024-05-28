package store.ppingpong.board.mock.image;

import store.ppingpong.board.image.application.port.ImageRepository;
import store.ppingpong.board.image.domain.Image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FakeImageRepository implements ImageRepository {
    private List<Image> data = new ArrayList<>();
    @Override
    public List<Image> saveList(List<Image> images) {
        data.addAll(images);
        return images;
    }

    @Override
    public List<Image> findByPostId(long postId) {
        return data;
    }

    @Override
    public int delete(long postId) {
        int count = 0;
        Iterator<Image> iterator = data.iterator();
        while (iterator.hasNext()) {
            Image image = iterator.next();
            if (image.getPostId() == postId) {
                iterator.remove();
                count++;
            }
        }
        return count;
    }
}
