package com.gcp.function.config;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SystemTimeZoneConfig {

    public static void setDefaultTimeZone(ZoneId zoneId) {

        TimeZone.setDefault(TimeZone.getTimeZone(zoneId));
    }

    public static void setUtcDefaultTimeZone() {

        setDefaultTimeZone(ZoneOffset.UTC);
    }

}
