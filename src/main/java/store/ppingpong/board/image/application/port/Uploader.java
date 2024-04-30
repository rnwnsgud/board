package store.ppingpong.board.image.application.port;

import org.springframework.web.multipart.MultipartFile;
import store.ppingpong.board.image.domain.Image;

import java.io.IOException;
import java.util.List;

public interface Uploader {
    List<Image> upload(List<MultipartFile> multipartFile, Long postId) throws IOException;
    void delete(List<Image> imageList);
}
