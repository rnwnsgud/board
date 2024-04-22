package store.ppingpong.board.mock.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.domain.PostWithWriter;
import store.ppingpong.board.post.application.port.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakePostRepository implements PostRepository {

    private final List<Post> data = new ArrayList<>();
    private static long sequence = 0L;
    @Override
    public Post create(Post post) {
        if (post.getId() == null || post.getId() == 0) {
            Post mockPost = Post.builder()
                    .id(++sequence)
                    .title(post.getTitle())
                    .content(post.getContent())
                    .userId(post.getUserId())
                    .forumId(post.getForumId())
                    .postType(post.getPostType())
                    .createdAt(post.getCreatedAt())
                    .build();
            data.add(mockPost);
            return mockPost;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), post.getId()));
            data.add(post);
            return post;
        }

    }

    @Override
    public Page<PostWithWriter> findByForumId(String forumId, Integer listNum, Pageable pageable) {
        List<Post> list = data.stream()
                .filter(post -> post.getForumId().equals(forumId))
                .toList();
        FakePage<Post> posts = new FakePage<>(list, pageable.getPageNumber(), pageable.getPageSize(), list.size());
//        posts.stream()
//                .map()
        return null;
    }

    @Override
    public Post findById(long id) {
        Post post = data.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .get();
        return post;
    }

    @Override
    public void inquiry(Post post) {

    }


}
