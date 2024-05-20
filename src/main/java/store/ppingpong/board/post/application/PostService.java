package store.ppingpong.board.post.application;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import store.ppingpong.board.common.config.auth.LoginUser;
import store.ppingpong.board.common.domain.ClockLocalHolder;
import store.ppingpong.board.forum.application.port.ForumManagerRepository;
import store.ppingpong.board.image.application.port.ImageRepository;
import store.ppingpong.board.image.domain.Image;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.domain.PostWithImages;
import store.ppingpong.board.post.domain.service.PostCreator;
import store.ppingpong.board.post.dto.PostCreate;
import store.ppingpong.board.post.domain.PostWithWriter;
import store.ppingpong.board.post.application.port.PostRepository;
import store.ppingpong.board.image.application.port.Uploader;
import store.ppingpong.board.post.dto.PostDeleteResponseDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Builder
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final ForumManagerRepository forumManagerRepository;
    private final PostCreator postCreator;
    private final ClockLocalHolder clockLocalHolder;
    private final ReadPostService readPostService;
    private final ImageRepository imageRepository;
    private final Uploader uploader;

    public PostWithImages create(PostCreate postCreate, Long userId, String forumId, List<MultipartFile> multipartFiles) throws IOException {
        // ForumManager인지 확인 후(아니라면 생성), Post 저장
        Post post = postCreator.create(postCreate, userId, forumId);
        // s3 이미지 업로드
        List<Image> images = new ArrayList<>();
        if (multipartFiles != null) images = uploader.upload(multipartFiles, post.getId());
        // 이미지 정보를 DB에 저장
        return PostWithImages.of(post, images);
    }


    @Transactional(readOnly = true)
    public Page<PostWithWriter> getList(String forumId, int listNum, Long search_head, Pageable pageable) {
        return postRepository.findByForumId(forumId, listNum, search_head, pageable);
    }
    @Transactional
    public PostWithImages findById(long id, LoginUser loginUser) {
        Post post = postRepository.findById(id);
        List<Image> images = imageRepository.findByPostId(post.getId());
        post = post.visit();
        postRepository.inquiry(post);
        readPostService.firstReadThenCreate(loginUser, post.getId());
        return PostWithImages.of(post, images);
    }

    @Transactional
    public PostDeleteResponseDto delete(long id, Long userId) {
        Post post = postRepository.findById(id);
        post.checkPostOwner(userId);
        int status = postRepository.delete(id);
        readPostService.deleteByPostId(post.getId());
        List<Image> imageList = imageRepository.findByPostId(post.getId());
        int deletedImageCount = imageRepository.delete(post.getId());
        uploader.delete(imageList);
        return new PostDeleteResponseDto(status, deletedImageCount);
    }


}
