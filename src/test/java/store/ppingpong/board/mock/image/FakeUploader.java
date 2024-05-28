package store.ppingpong.board.mock.image;

import org.springframework.web.multipart.MultipartFile;
import store.ppingpong.board.image.application.port.Uploader;
import store.ppingpong.board.image.domain.FileExtension;
import store.ppingpong.board.image.domain.Image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class FakeUploader implements Uploader {

    public List<Image> data = new ArrayList<>();
    @Override
    public List<Image> upload(List<MultipartFile> multipartFiles, Long postId) {
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
            data.add(image);
        }
        return data;
    }

    @Override
    public void delete(List<Image> imageList) {
        Iterator<Image> iterator = data.iterator();
        while (iterator.hasNext()) {
            Image next = iterator.next();
            for (Image image : imageList) {
                if (next.getOriginalName().equals(image.getOriginalName())) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    @Override
    public String generatePreSignUrl(String fileName) {
        return null;
    }
}
