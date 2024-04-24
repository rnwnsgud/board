package store.ppingpong.board.image.infrastructure;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import store.ppingpong.board.common.domain.RandomHolder;
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
    private final FileTransmitter fileTransmitter;
    private final ImageRepository imageRepository;
    private final RandomHolder randomHolder;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${file.dir}")
    private String dir;

    @Override
    public List<Image> upload(List<MultipartFile> images, Long postId) throws IOException {
        List<Image> uploadImages = fileTransmitter.storeFiles(images, postId, amazonS3Client, randomHolder, dir, bucket);
        return imageRepository.saveList(uploadImages);
    }

}
