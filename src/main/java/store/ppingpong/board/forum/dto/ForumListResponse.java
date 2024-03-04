package store.ppingpong.board.forum.dto;

import lombok.Getter;
import store.ppingpong.board.forum.domain.Forum;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ForumListResponse {

    private final List<ForumDto> forumDtoList;

    private ForumListResponse(List<ForumDto> forumList) {
        this.forumDtoList = forumList;
    }

    @Getter
    public static class ForumDto {
        private final String forumId;
        private final String name;

        public ForumDto(Forum forum) {
            this.forumId = forum.getForumId();
            this.name = forum.getName();
        }
    }
    public static ForumListResponse from(List<Forum> forumList) {
        List<ForumDto> forumDtoList = forumList.stream()
                .map(ForumDto::new)
                .collect(Collectors.toList());
        return new ForumListResponse(forumDtoList);
    }
}
