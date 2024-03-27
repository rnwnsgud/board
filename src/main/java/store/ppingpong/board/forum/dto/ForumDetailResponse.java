package store.ppingpong.board.forum.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.post.domain.Post;
import store.ppingpong.board.post.domain.PostType;
import store.ppingpong.board.user.domain.User;

import java.util.List;
@Getter
public class ForumDetailResponse {

    private final String name;
    private final String introduction;
    private final String managerName;
    private final List<String> assistantNames;
    private final List<PostDto> postDtoList;

    private ForumDetailResponse(String name, String introduction, String managerName, List<String> assistantNames, Page<Post> postPage) {
        this.name = name;
        this.introduction = introduction;
        this.managerName = managerName;
        this.assistantNames = assistantNames;
        this.postDtoList = postPage.map(PostDto::from).toList();
    }

    public static ForumDetailResponse of(Forum forum, User forumManager, List<User> forumAssistant, Page<Post> postPage) {
        String managerName = forumManager.getUserInfo().getNickname() + "(" + forumManager.getLoginInfo().getLoginId() + ")";
        List<String> assistantNames = forumAssistant.stream()
                .map(assistant -> assistant.getUserInfo().getNickname() + "(" + assistant.getLoginInfo().getLoginId() + ")")
                .toList();
        return new ForumDetailResponse(forum.getName(), forum.getIntroduction(), managerName, assistantNames, postPage);
    }

    @Getter
    @Builder
    public static class PostDto {
        private Long id;
        private String title;
        private PostType postType;
//        private String writer;

        public static PostDto from(Post post) {
            return PostDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .postType(post.getPostType())
                    .build();
        }
    }

}

