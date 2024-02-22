package store.ppingpong.board.forum.dto;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.user.domain.User;

@Getter
@Builder
public class ForumAssistantResponse {
    private String forumId;
    private String loginId;
    private String nickname;

    public static ForumAssistantResponse of(String forumId, User user) {
        return ForumAssistantResponse.builder()
                .forumId(forumId)
                .loginId(user.getLoginInfo().getLoginId())
                .nickname(user.getUserInfo().getNickname())
                .build();
    }
}
