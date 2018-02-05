package com.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.File;
import java.io.IOException;

public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static String getLoginFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public static <T> void dumpData(Class clazz, T source, String path) {
        File file = new File(path + clazz.getSimpleName() + ".json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, source);
        } catch (IOException e) {
            log.info("In Utils while serializing");
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T loadData(Class clazz, String path) {
        Object data = null;
        File file = new File(path + clazz.getSimpleName() + ".json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            data = mapper.readValue(file, clazz);
        } catch (IOException e) {
            log.info("In Utils while deserializing");
        }
        return (T) data;
    }
}
