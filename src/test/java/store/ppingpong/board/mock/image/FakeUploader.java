package store.ppingpong.board.mock.image;

import org.springframework.web.multipart.MultipartFile;
import store.ppingpong.board.image.application.port.Uploader;
import store.ppingpong.board.image.domain.FileExtension;
import store.ppingpong.board.image.domain.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FakeUploader implements Uploader {

    public List<Image> images = new ArrayList<>();
    @Override
    public List<Image> upload(List<MultipartFile> multipartFiles, Long postId) throws IOException {
        for (MultipartFile multipartFile : multipartFiles) {
            String originalFilename = multipartFile.getOriginalFilename();
            assert originalFilename != null;
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            FileExtension fileExtension = FileExtension.JPEG;
            for (FileExtension x : FileExtension.values()) {
                if (x.name().compareToIgnoreCase(extension) == 0) fileExtension = x;
            }
            Image image = Image.builder()
                    .postId(postId)
                    .originalName(multipartFile.getOriginalFilename())
                    .storedName(UUID.randomUUID().toString())
                    .fileExtension(fileExtension)
                    .build();
            images.add(image);
        }
        return images;
    }

    @Override
    public void delete(List<Image> imageList) {

    }

    @Override
    public String generatePreSignUrl(String fileName) {
        return null;
    }
}
