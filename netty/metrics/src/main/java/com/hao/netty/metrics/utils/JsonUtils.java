package com.hao.netty.metrics.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class JsonUtils {
    private static final ObjectMapper objMapper = new ObjectMapper();

    @SneakyThrows
    public static final String prettyJson(Object obj) {
        return objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

    public static final void printPrettyJson(Object obj) {
        System.err.println(prettyJson(obj));
    }
}
