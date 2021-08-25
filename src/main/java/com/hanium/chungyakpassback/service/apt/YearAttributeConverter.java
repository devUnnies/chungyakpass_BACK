package com.hanium.chungyakpassback.service.apt;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class YearAttributeConverter implements AttributeConverter<YearMonth, String>{
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

        @Override
        public String convertToDatabaseColumn(YearMonth yearMonth) {
            return yearMonth.format(FORMATTER);
        }

        @Override
        public YearMonth convertToEntityAttribute(String yearMonth) {
            return YearMonth.parse(yearMonth);
        }
}

