package store.ppingpong.board.forum.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.post.domain.PostWithWriter;
import store.ppingpong.board.user.domain.User;

import java.util.List;
@Getter
public class ForumDetailResponse {

    private final String name;
    private final String introduction;
    private final String managerName;
    private final List<String> assistantNames;
    private final Page<PostWithWriter> postWithWriters;

    private ForumDetailResponse(String name, String introduction, String managerName, List<String> assistantNames, Page<PostWithWriter> postWithWriters) {
        this.name = name;
        this.introduction = introduction;
        this.managerName = managerName;
        this.assistantNames = assistantNames;
        this.postWithWriters = postWithWriters;
    }

    public static ForumDetailResponse of(Forum forum, User forumManager, List<User> forumAssistant, Page<PostWithWriter> postWithWriters) {
        String managerName = forumManager.getUserInfo().getNickname() + "(" + forumManager.getLoginInfo().getLoginId() + ")";
        List<String> assistantNames = forumAssistant.stream()
                .map(assistant -> assistant.getUserInfo().getNickname() + "(" + assistant.getLoginInfo().getLoginId() + ")")
                .toList();
        return new ForumDetailResponse(forum.getName(), forum.getIntroduction(), managerName, assistantNames, postWithWriters);
    }

}

