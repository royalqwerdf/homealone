package com.elice.homealone.global.crawler;

import java.util.Base64;
import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class BinaryToStringConverter implements Converter<Binary, String> {

    @Override
    public String convert(Binary source) {
        // Binary 데이터를 String으로 변환하는 로직을 작성합니다.
        // 예를 들어, Base64 인코딩을 사용할 수 있습니다.
        return Base64.getEncoder().encodeToString(source.getData());
    }
}
