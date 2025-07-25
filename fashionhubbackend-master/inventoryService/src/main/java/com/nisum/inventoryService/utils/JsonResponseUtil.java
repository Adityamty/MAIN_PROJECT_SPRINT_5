package com.nisum.inventoryService.utils;

import java.util.HashMap;
import java.util.Map;

public class JsonResponseUtil {

    public static Map<String, String> createMessage(String message) {
        Map<String, String> map = new HashMap<>();
        map.put("message", message);
        return map;
    }

    public static Map<String, String> createError(String error) {
        Map<String, String> map = new HashMap<>();
        map.put("error", error);
        return map;
    }
}
