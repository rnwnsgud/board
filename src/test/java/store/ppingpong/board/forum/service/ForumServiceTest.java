package store.ppingpong.board.forum.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.ppingpong.board.forum.domain.*;
import store.ppingpong.board.forum.dto.ForumCreate;
import store.ppingpong.board.mock.forum.FakeForumRepository;
import store.ppingpong.board.mock.forum.FakeForumManagerRepository;
import store.ppingpong.board.mock.forum.TestClockLocalHolder;
import store.ppingpong.board.user.domain.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ForumServiceTest {

    private ForumServiceImpl forumService;

    @BeforeEach
    void init() {
        FakeForumRepository fakeForumRepository = new FakeForumRepository();
        FakeForumManagerRepository fakeForumUserRepository = new FakeForumManagerRepository();

        this.forumService = ForumServiceImpl.builder()
                .clockLocalHolder(new TestClockLocalHolder(LocalDateTime.MIN))
                .forumRepository(fakeForumRepository)
                .forumManagerRepository(fakeForumUserRepository)
                .build();

        Forum forum = Forum.builder()
                .forumId("bg3")
                .name("발더스 게이트 3")
                .introduction("라리안 스튜디오에서 제작한 턴제 RPG, 공식 한국어 지원")
                .forumStatus(ForumStatus.PENDING)
                .category(Category.GAME)
                .createdAt(LocalDateTime.MIN)
                .build();
        UserInfo userInfo = UserInfo.builder()
                .userEnum(UserEnum.USER)
                .email("ssar@naver.com")
                .nickname("쌀")
                .build();
        LoginInfo loginInfo = LoginInfo.builder()
                .loginId("ssar1234")
                .encodePassword("1234ABCD56")
                .loginType(LoginType.NAVER)
                .build();
        User user = User.builder()
                .id(1L)
                .userStatus(UserStatus.ACTIVE)
                .userInfo(userInfo)
                .loginInfo(loginInfo)
                .createdAt(100L).build();
        fakeForumRepository.save(forum);
        fakeForumUserRepository.save(ForumManager.builder()
                .id(1L)
                .userId(1L)
                .forumId("bg3")
                .forumManagerLevel(ForumManagerLevel.MANAGER)
                .build());

    }

    @Test
    void 일반유저는_ForumCreate로_포럼을_생성할_수_있고_PENDING_상태다() {
        // given
        ForumCreate forumCreate = ForumCreate.builder()
                .forumId("reverse1999")
                .name("리버스1999")
                .introduction("리버스1999 입니다.")
                .category(Category.GAME)
                .build();

        UserInfo userInfo = UserInfo.builder()
                .userEnum(UserEnum.USER)
                .email("cos@google.com")
                .nickname("코스")
                .build();

        LoginInfo loginInfo = LoginInfo.builder()
                .loginId("cos1234")
                .encodePassword("1234ABCD56")
                .loginType(LoginType.GOOGLE)
                .build();

        User user = User.builder()
                .id(2L)
                .userStatus(UserStatus.ACTIVE)
                .userInfo(userInfo)
                .loginInfo(loginInfo)
                .createdAt(100L).build();
        // when
        Forum forum = forumService.create(forumCreate, user);

        // then
        assertThat(forum.getForumId()).isEqualTo("reverse1999");
        assertThat(forum.getName()).isEqualTo("리버스1999");
        assertThat(forum.getIntroduction()).isEqualTo("리버스1999 입니다.");
        assertThat(forum.getCategory()).isEqualTo(Category.GAME);
        assertThat(forum.getForumStatus()).isEqualTo(ForumStatus.PENDING);
        assertThat(forum.getCreatedAt()).isEqualTo(LocalDateTime.MIN);
    }

    @Test
    void 운영자는_ForumCreate로_포럼을_생성할_수_있고_ACTIVE_상태다() {
        // given
        ForumCreate forumCreate = ForumCreate.builder()
                .forumId("reverse1999")
                .name("리버스1999")
                .introduction("리버스1999 입니다.")
                .category(Category.GAME)
                .build();

        UserInfo userInfo = UserInfo.builder()
                .userEnum(UserEnum.ADMIN)
                .email("cos@google.com")
                .nickname("코스")
                .build();

        LoginInfo loginInfo = LoginInfo.builder()
                .loginId("cos1234")
                .encodePassword("1234ABCD56")
                .loginType(LoginType.GOOGLE)
                .build();

        User user = User.builder()
                .id(2L)
                .userStatus(UserStatus.ACTIVE)
                .userInfo(userInfo)
                .loginInfo(loginInfo)
                .createdAt(100L).build();

        // when
        Forum forum = forumService.create(forumCreate, user);

        // then
        assertThat(forum.getForumId()).isEqualTo("reverse1999");
        assertThat(forum.getName()).isEqualTo("리버스1999");
        assertThat(forum.getIntroduction()).isEqualTo("리버스1999 입니다.");
        assertThat(forum.getCategory()).isEqualTo(Category.GAME);
        assertThat(forum.getForumStatus()).isEqualTo(ForumStatus.ACTIVE);
        assertThat(forum.getCreatedAt()).isEqualTo(LocalDateTime.MIN);
    }

    @Test
    void getActiveList는_ACTIVE상태인_포럼만_가져온다() {
        // given
        ForumCreate forumCreate = ForumCreate.builder()
                .forumId("reverse1999")
                .name("리버스1999")
                .introduction("리버스1999 입니다.")
                .category(Category.GAME)
                .build();

        UserInfo userInfo = UserInfo.builder()
                .userEnum(UserEnum.ADMIN)
                .email("cos@google.com")
                .nickname("코스")
                .build();

        LoginInfo loginInfo = LoginInfo.builder()
                .loginId("cos1234")
                .encodePassword("1234ABCD56")
                .loginType(LoginType.GOOGLE)
                .build();

        User user = User.builder()
                .id(2L)
                .userStatus(UserStatus.ACTIVE)
                .userInfo(userInfo)
                .loginInfo(loginInfo)
                .createdAt(100L).build();

        // when
        forumService.create(forumCreate, user);
        List<Forum> forums = forumService.getActiveList();

        // then
        assertThat(forums.size()).isEqualTo(1);
        assertThat(forums.get(0).getForumId()).isEqualTo("reverse1999");
        assertThat(forums.get(0).getName()).isEqualTo("리버스1999");
        assertThat(forums.get(0).getCategory()).isEqualTo(Category.GAME);
        assertThat(forums.get(0).getForumStatus()).isEqualTo(ForumStatus.ACTIVE);
        assertThat(forums.get(0).getIntroduction()).isEqualTo("리버스1999 입니다.");

    }

    @Test
    void findById로_forumId와_같은_포럼을_가져올_수_있다() {
        // when
        Forum forum = forumService.findById("bg3");

        // then
        assertThat(forum.getForumId()).isEqualTo("bg3");
        assertThat(forum.getName()).isEqualTo("발더스 게이트 3");
        assertThat(forum.getCreatedAt()).isEqualTo(LocalDateTime.MIN);
        assertThat(forum.getForumStatus()).isEqualTo(ForumStatus.PENDING);
        assertThat(forum.getCategory()).isEqualTo(Category.GAME);
        assertThat(forum.getIntroduction()).isEqualTo("라리안 스튜디오에서 제작한 턴제 RPG, 공식 한국어 지원");
    }
}
