package store.ppingpong.board.forum.dto;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.domain.ForumManager;
import store.ppingpong.board.forum.domain.ForumManagerLevel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class ForumDetailResponse {

    private final String name;
    private final String introduction;
    private final String manager;
    private final List<String> assistants;

    public ForumDetailResponse(Forum forum, List<ForumManager> forumManagers) {
        this.name = forum.getName();
        this.introduction = forum.getIntroduction();

        Optional<String> managerName = forumManagers.stream()
                .filter(manager -> manager.getForumUserLevel() == ForumManagerLevel.MANAGER)
                .map(manager -> manager.getUser().getUserInfo().getNickname() + "(" + manager.getUser().getLoginInfo().getLoginId() + ")")
                .findFirst();

        this.manager = managerName.orElse("");

        this.assistants = forumManagers.stream()
                .filter(manager -> manager.getForumUserLevel() == ForumManagerLevel.ASSISTANT)
                .map(manager -> manager.getUser().getUserInfo().getNickname() + "(" + manager.getUser().getLoginInfo().getLoginId() + ")")
                .collect(Collectors.toList());

    }
}

