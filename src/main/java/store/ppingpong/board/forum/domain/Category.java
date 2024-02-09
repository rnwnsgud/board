package store.ppingpong.board.forum.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
    ENTERTAINMENT("연예"), BROADCAST("방송"), GAME("게임"), HOBBY("취미"),
    CARTOON("만화"), SPORT("운동"), IT("IT"), FOOD("음식"),
    TRIP("여행"), BIOLOGY("생물"), HEALTH("건강"), EDUCATION("교육"),
    UNIVERSITY("대학"), JOB("직업"), MUSIC("음악"), SHOPPING("쇼핑"),
    ADULT("성인"), ETC("기타"), ISSUE("이슈"), FASHION("패션");

    private final String value;
}
