package store.ppingpong.board.image.domain.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import store.ppingpong.board.common.handler.exception.FileNotDeletedException;
import store.ppingpong.board.common.handler.exception.FileNotSupportedException;
import store.ppingpong.board.common.handler.exception.FileUploadException;
import store.ppingpong.board.image.domain.FileExtension;
import store.ppingpong.board.image.domain.Image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileConverter {

    public List<Image> storeFiles(List<MultipartFile> multipartFiles, Long postId,
                                  AmazonS3Client amazonS3Client,
                                  String localDirectoryLocation, String bucket) throws IOException {
        List<Image> images = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            String originalName = multipartFile.getOriginalFilename();
            if (originalName==null || originalName.isBlank()) throw new FileUploadException();
            String storedFileName = createStoreFileName(originalName);
            File localFile = saveFileInLocal(localDirectoryLocation, storedFileName, multipartFile);
            uploadToS3(amazonS3Client, bucket, storedFileName, localFile);
            removeNewFile(localFile);
            String s3Url = amazonS3Client.getUrl(bucket, storedFileName).toString();
            Image image = Image.of(postId, multipartFile.getOriginalFilename(), storedFileName, s3Url, getFileExtension(originalName));
            images.add(image);
        }
        return images;
    }

    private static void uploadToS3(AmazonS3Client amazonS3Client, String bucket, String storeFileName, File localFile) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, storeFileName, localFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private static File saveFileInLocal(String localDirectoryLocation, String storeFileName, MultipartFile multipartFile) throws IOException {
        String localPath = localDirectoryLocation + storeFileName;
        File localFile = new File(localPath);
        multipartFile.transferTo(localFile);
        return localFile;
    }

    private static void removeNewFile(File file) {
        if (!file.delete()) {
            throw new FileNotDeletedException();
        }
    }

    private static String createStoreFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName).toString());
    }

    private static FileExtension getFileExtension(String fileName) {
        if (fileName.isEmpty()) {
            throw new FileUploadException();
        }
        return checkSupportedExtension(fileName.substring(fileName.lastIndexOf(".")+1));
    }

    private static FileExtension checkSupportedExtension(String extension) {
        for (FileExtension fileExtension : FileExtension.values()) {
            if (fileExtension.name().equals(extension)) return fileExtension;
        }
        throw new FileNotSupportedException();
    }
}
