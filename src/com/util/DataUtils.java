package com.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtils extends HashMap<String, Object> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataUtils() {
        super();
    }

    public DataUtils(int code) {
        super();
        this.put("code", code);
    }
    public DataUtils(String  code, Object message) {
        super();
        this.put("code", code);
        this.put("message", message);
    }

    public DataUtils(int code, String message) {
        super();
        this.put("code", code);
        this.put("message", message);
    }

    public DataUtils(int code, Map<String, Object> data, String message) {
        super();
        this.put("code", code);
        this.put("message", message);
        this.put("data", data);
    }

    public DataUtils(int code, Map<String, Object> data) {
        super();
        this.put("code", code);
        this.put("data", data);
    }

    public DataUtils(int code, List<Map<String, Object>> data) {
        super();
        this.put("code", code);
        this.put("data", data);
    }

    public DataUtils(int code, Object data) {
        super();
        this.put("code", code);
        this.put("data", data);
    }

    public static DataUtils getInstance() {
        return new DataUtils();
    }
    public static DataUtils getInstance(String  code, Object message) {
        return new DataUtils(code,message);
    }
    public static DataUtils getInstance(int code) {
        return new DataUtils(code);
    }

    public static DataUtils getInstance(int code, String message) {
        return new DataUtils(code,message);
    }

    public static DataUtils getInstance(int code, Map<String, Object> data) {
        return new DataUtils(code,data);
    }

    public static DataUtils getInstance(int code, List<Map<String, Object>> data) {
        return new DataUtils(code,data);
    }

    public static DataUtils getInstance(int code, Object data) {
        return new DataUtils(code,data);
    }

    public static DataUtils getInstance(int code, Map<String, Object> data, String message) {
        return new DataUtils(code,data,message);
    }


}
