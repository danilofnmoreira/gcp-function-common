package com.gcp.function.config;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectMapperConfig {

    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneOffset.UTC);
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {

        configDateTimeModule(OBJECT_MAPPER);

    }

    public static void configDateTimeModule(ObjectMapper objM) {

        objM.registerModule(createJavaZonedTimeModule());
        objM.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static JavaTimeModule createJavaZonedTimeModule() {

        var dateTimeModule = new JavaTimeModule();
        dateTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DEFAULT_DATE_TIME_FORMATTER));
        dateTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DEFAULT_DATE_TIME_FORMATTER));
        dateTimeModule.addDeserializer(ZonedDateTime.class, InstantDeserializer.ZONED_DATE_TIME);
        dateTimeModule.addSerializer(ZonedDateTime.class, ZonedDateTimeSerializer.INSTANCE);

        return dateTimeModule;
    }

}
