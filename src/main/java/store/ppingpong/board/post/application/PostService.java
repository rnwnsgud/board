package store.ppingpong.board.post.application;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import store.ppingpong.board.common.domain.ClockLocalHolder;
import store.ppingpong.board.forum.service.port.ForumManagerRepository;
import store.ppingpong.board.image.application.port.ImageRepository;
import store.ppingpong.board.image.domain.Image;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.domain.PostWithImages;
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
@Transactional
@Service
public class PostService {
    private final PostRepository postRepository;
    private final ForumManagerRepository forumManagerRepository;
    private final ClockLocalHolder clockLocalHolder;
    private final ReadPostService readPostService;
    private final ImageRepository imageRepository;
    private final Uploader uploader;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public PostWithImages create(PostCreate postCreate, Long userId, String forumId, List<MultipartFile> multipartFiles) throws IOException {
        forumManagerRepository.findForumUserOrCreate(forumId, userId).isAccessible();
        Post post = postRepository.create(Post.of(postCreate, userId, forumId, clockLocalHolder));
        List<Image> images = new ArrayList<>();
        if (multipartFiles != null) images = uploader.upload(multipartFiles, post.getId());
        return PostWithImages.of(post, images);
    }

    @Transactional(readOnly = true)
    public Page<PostWithWriter> getList(String forumId, int listNum, Long search_head, Pageable pageable) {
        return postRepository.findByForumId(forumId, listNum, search_head, pageable);
    }

    public PostWithImages findById(long id, Long visitorId) {
        Post post = postRepository.findById(id);
        List<Image> imageList = imageRepository.findByPostId(post.getId());
        post = post.visit(visitorId);
        postRepository.inquiry(post);
        readPostService.firstReadThenCreate(visitorId, post.getId());
        return PostWithImages.of(post, imageList);
    }

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
