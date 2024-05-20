package store.ppingpong.board.image.infrastructure;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import store.ppingpong.board.image.domain.Image;
import store.ppingpong.board.image.domain.service.FileTransmitter;
import store.ppingpong.board.image.application.port.ImageRepository;
import store.ppingpong.board.image.application.port.Uploader;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@Component
public class S3Uploader implements Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${file.dir}")
    private String dir;

    @Override
    public List<Image> upload(List<MultipartFile> images, Long postId) throws IOException {
        List<Image> imageList = FileTransmitter.storeFiles(images, postId, amazonS3Client, dir, bucket);
        if (imageList.isEmpty()) throw new FileUploadException("파일이 정상적으로 업로드 되지 않았습니다.");
        return imageList;
    }

    @Override
    public void delete(List<Image> imageList) {
        for (Image image : imageList) {
            amazonS3Client.deleteObject(bucket, image.getStoredName());
        }
    }

    @Override
    public String generatePreSignUrl(String fileName) {
        return null;
    }


}
