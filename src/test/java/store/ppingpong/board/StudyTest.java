package store.ppingpong.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.infrastructure.ForumEntity;
import store.ppingpong.board.forum.infrastructure.ForumJpaRepository;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.infrastructure.UserEntity;
import store.ppingpong.board.user.infrastructure.UserJpaRepository;

@SpringBootTest
public class StudyTest {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    ForumJpaRepository forumJpaRepository;

    @Test
    void generatedValueSave() {
        User user = User.builder()
                .build();
        UserEntity userEntity = UserEntity.from(user);
        userJpaRepository.save(userEntity);
    }

    @Test
    void directIdSave() {
        Forum forum = Forum.builder().forumId("forum1234").build();
        ForumEntity forumEntity = ForumEntity.from(forum);
        forumJpaRepository.save(forumEntity);
    }


}
