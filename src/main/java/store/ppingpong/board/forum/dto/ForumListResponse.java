package store.ppingpong.board.forum.dto;

import lombok.Getter;
import store.ppingpong.board.forum.domain.Forum;

import java.util.List;
import java.util.stream.Collectors;


public class ForumListResponse {

    List<ForumDto> forumDtoList;

    public ForumListResponse(List<Forum> forumList) {
        this.forumDtoList = forumList.stream().map(ForumDto::new).collect(Collectors.toList());
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
}
