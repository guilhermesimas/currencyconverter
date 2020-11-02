package com.cortex.currencyconverter.services.keygenerators;

import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;

public class ConversionKeyGenerator extends SimpleKeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return super.generate(target, method, Arrays.copyOfRange(params, 0, 4));
    }
}
