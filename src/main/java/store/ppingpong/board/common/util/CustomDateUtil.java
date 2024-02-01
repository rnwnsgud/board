package store.ppingpong.board.common.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CustomDateUtil {
    public static String convertToStringForHuman(long utcMillis, String formatPattern) {
        Instant instant = Instant.ofEpochMilli(utcMillis);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern)
                .withZone(ZoneId.of("UTC"));

        return formatter.format(instant);
    }
}
