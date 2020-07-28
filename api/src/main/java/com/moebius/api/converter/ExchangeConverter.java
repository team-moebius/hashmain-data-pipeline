package com.moebius.api.converter;

import com.moebius.data.type.Exchange;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ExchangeConverter implements Converter<String, Exchange> {
    @Override
    public Exchange convert(String source) {
        // TODO: null exception
        return Exchange.from(source);
    }
}
