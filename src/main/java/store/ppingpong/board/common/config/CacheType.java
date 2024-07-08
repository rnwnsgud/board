package store.ppingpong.board.common.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CacheType {
    NOTICE("notice", 12, 10000);

    private final String cacheName;
    private final int secsToExpireAfterWrite;
    private final int entryMaxSize;
}
