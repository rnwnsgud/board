package store.ppingpong.board.post.application;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
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
import store.ppingpong.board.post.dto.PostDeleteResponse;
import store.ppingpong.board.post.dto.PostModifyRequest;
import store.ppingpong.board.reaction.domain.Reaction;
import store.ppingpong.board.reaction.domain.ReactionType;
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
    private final ReactionRepository reactionRepository;

    public PostCreateResponse create(PostCreateRequest postCreateRequest, Long userId, String forumId, List<MultipartFile> multipartFiles) throws IOException {
        Post post = postCreator.create(postCreateRequest, userId, forumId);
        List<Image> images = uploadImages(multipartFiles, post);
        return PostCreateResponse.of(post, images);
    }

    private List<Image> uploadImages(List<MultipartFile> multipartFiles, Post post) throws IOException {
        List<Image> images = new ArrayList<>();
        if (multipartFiles != null) images = uploader.upload(multipartFiles, post.getId());
        images = imageRepository.saveList(images);
        return images;
    }

    @Transactional(readOnly = true)
    public Page<PostWithWriter> getList(String forumId, int pageSize, Long search_head, Pageable pageable) {
        return postRepository.findByForumId(forumId, pageSize, search_head, pageable);
    }

    @Cacheable("notice")
    @Transactional(readOnly = true)
    public List<PostWithWriter> getNotice(String forumId) {
        return postRepository.getNotice(forumId);
    }


    @Transactional
    public PostWithImages getById(long id, LoginUser loginUser) {
        Post post = postRepository.getById(id);
        List<Image> images = imageRepository.findByPostId(post.getId());
        readPostService.firstReadThenCreate(loginUser, post.getId());
        // TODO : 요청마다 좋아요를 긁어오는 건 비효율적이다. 그래서 post 필드에 likeCount를 추가했지만 정합성에 문제가 발생한다.
        //  스케줄링으로 주기적으로 정합성을 맞춰줘야 하지만 모든 post에 적용하는 것은 batch 지식이 필요해 보인다.
        List<Reaction> reactions = reactionRepository.findByTargetTypeAndId(TargetType.POST, id);
        post = post.visit();
        postRepository.inquiry(post);
        return PostWithImages.of(post, images, reactions);
    }

    @Transactional
    public PostDeleteResponse delete(long id, Long userId) {
        Post post = getWithCheckOwner(id, userId);
        int status = postRepository.delete(id);
        List<Image> imageList = imageRepository.findByPostId(post.getId());
        int deletedCount = imageRepository.delete(post.getId());
        uploader.delete(imageList);
        readPostService.deleteByPostId(post.getId());
        return new PostDeleteResponse(status, deletedCount);
    }

    private Post getWithCheckOwner(long id, Long userId) {
        Post post = postRepository.getById(id);
        post.checkPostOwner(userId);
        return post;
    }

    @Transactional
    public void react(boolean react, long id, ReactionType reactionType) {
        if (!react) return;
        Post post = postRepository.getById(id);
        post = post.like(reactionType);
        postRepository.create(post);
    }

    @Transactional
    public PostWithImages modify(long postId, long userId, PostModifyRequest postModifyRequest, List<MultipartFile> multipartFiles) throws IOException {
        Post post = getWithCheckOwner(postId, userId);
        imageRepository.delete(postId);
        post = postModifyRequest.toEntity(post);
        postRepository.modify(post);
        List<Image> images = uploadImages(multipartFiles, post);
        return PostWithImages.modify(post, images);
    }

}
