package store.ppingpong.board.image.domain.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;
import store.ppingpong.board.common.handler.exception.file.FileNotDeletedException;
import store.ppingpong.board.common.handler.exception.file.FileNotSupportedException;
import store.ppingpong.board.common.handler.exception.file.FileUploadException;
import store.ppingpong.board.image.domain.FileExtension;
import store.ppingpong.board.image.domain.Image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileTransmitter {

    public static List<Image> storeFiles(List<MultipartFile> multipartFiles, Long postId,
                                  AmazonS3Client amazonS3Client,
                                  String localDirectoryLocation, String bucket) throws IOException {
        List<Image> images = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            String originalName = multipartFile.getOriginalFilename();
            if (originalName==null || originalName.isBlank()) throw new FileUploadException();
            String storedFileName = createStoreFileName(originalName);
            uploadS3AndDeleteInLocal(amazonS3Client, localDirectoryLocation, bucket, multipartFile, storedFileName);
            makeImages(postId, amazonS3Client, bucket, multipartFile, storedFileName, originalName, images);
        }
        return images;
    }

    private static void uploadS3AndDeleteInLocal(AmazonS3Client amazonS3Client, String localDirectoryLocation, String bucket, MultipartFile multipartFile, String storedFileName) throws IOException {
        File localFile = saveFileInLocal(localDirectoryLocation, storedFileName, multipartFile);
        uploadToS3(amazonS3Client, bucket, storedFileName, localFile);
        removeNewFile(localFile);
    }

    private static void makeImages(Long postId, AmazonS3Client amazonS3Client, String bucket, MultipartFile multipartFile, String storedFileName, String originalName, List<Image> images) {
        String s3Url = amazonS3Client.getUrl(bucket, storedFileName).toString();
        Image image = Image.of(postId, multipartFile.getOriginalFilename(), storedFileName, s3Url, getFileExtension(originalName));
        images.add(image);
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
            if (fileExtension.name().toLowerCase().equals(extension)) return fileExtension;
        }
        throw new FileNotSupportedException();
    }
}
