package com.moebius.api.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class TimeUtils {

    public static ZonedDateTime getRoundUpTimeWithUTC(ZonedDateTime time) {
        return getRoundUpTime(time).withZoneSameInstant(ZoneId.of("UTC"));
    }

    public static ZonedDateTime getRoundUpTime(ZonedDateTime time) {
        ZonedDateTime newTime = time.getSecond() > 0 ? time.plusMinutes(1).withSecond(0) : time;
        return newTime.withNano(0);
    }

    public static ZonedDateTime getRoundDownTime(ZonedDateTime time) {
        ZonedDateTime newTime = time.getSecond() > 0 ? time.withSecond(0) : time;
        return newTime.withNano(0);
    }
}
