package store.ppingpong.board.post.application;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

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
import store.ppingpong.board.post.dto.PostCreateRequest;
import store.ppingpong.board.post.domain.PostWithWriter;
import store.ppingpong.board.post.application.port.PostRepository;
import store.ppingpong.board.image.application.port.Uploader;
import store.ppingpong.board.post.dto.PostCreateResponse;
import store.ppingpong.board.post.dto.PostDeleteResponseDto;
import store.ppingpong.board.reaction.application.ReactionService;
import store.ppingpong.board.reaction.domain.Reaction;
import store.ppingpong.board.reaction.domain.TargetType;
import store.ppingpong.board.reaction.infrastructure.ReactionRepository;

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
    private final ImageRepository imageRepository;
    private final Uploader uploader;
    private final ReadPostService readPostService;
    private final ReactionService reactionService;
    private final ReactionRepository reactionRepository;

    public PostCreateResponse create(PostCreateRequest postCreateRequest, Long userId, String forumId, List<MultipartFile> multipartFiles) throws IOException {
        Post post = postCreator.create(postCreateRequest, userId, forumId);
        List<Image> images = new ArrayList<>();
        if (multipartFiles != null) images = uploader.upload(multipartFiles, post.getId());
        return PostCreateResponse.of(post, images);
    }

    @Transactional(readOnly = true)
    public Page<PostWithWriter> getList(String forumId, int listNum, Long search_head, Pageable pageable) {
        return postRepository.findByForumId(forumId, listNum, search_head, pageable);
    }
    @Transactional
    public PostWithImages findById(long id, LoginUser loginUser) {
        Post post = postRepository.findById(id);
        List<Image> images = imageRepository.findByPostId(post.getId());
        readPostService.firstReadThenCreate(loginUser, post.getId());
        List<Reaction> reactions = reactionRepository.findByTargetTypeAndId(TargetType.POST, id);
        post = post.visit();
        postRepository.inquiry(post);
        return PostWithImages.of(post, images, reactions);
    }

    @Transactional
    public PostDeleteResponseDto delete(long id, Long userId) {
        Post post = postRepository.findById(id);
        post.checkPostOwner(userId);
        int status = postRepository.delete(id);
        List<Image> imageList = imageRepository.findByPostId(post.getId());
        int deletedImageCount = imageRepository.delete(post.getId());
        uploader.delete(imageList);
        readPostService.deleteByPostId(post.getId());
        return new PostDeleteResponseDto(status, deletedImageCount);
    }


}
