package store.ppingpong.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import store.ppingpong.board.common.infrastructure.RedisService;
import store.ppingpong.board.forum.domain.Forum;
import store.ppingpong.board.forum.infrastructure.ForumEntity;
import store.ppingpong.board.forum.infrastructure.ForumJpaRepository;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.infrastructure.UserEntity;
import store.ppingpong.board.user.infrastructure.UserJpaRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class StudyTest {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    ForumJpaRepository forumJpaRepository;

    @Autowired
    RedisService redisService;


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

    @Test
    void 레디스는_트랜잭션이_적용되지_않는다_그러나_추가설정으로_해결했다() {
        assertThatThrownBy( () -> redisService.save("key", "ex")).isInstanceOf(RuntimeException.class);
        assertThat(redisService.getValue("key")).isNull();
    }


}
