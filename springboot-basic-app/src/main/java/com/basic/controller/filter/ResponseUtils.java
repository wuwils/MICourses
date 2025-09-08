package com.basic.controller.filter;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class ResponseUtils {
    public enum Error {
        Ok(0, "ok"),
        Unknown(-1, "unknown error"),
        Internal(1000, "internal error"),
        CompileError(1001, "compile error"),
        InvalidJson(1002, "invalid json"),
        ;
        int code;
        String msg;
        int httpStatus;
        Error(int code, String msg) {
            this(code, msg, 200);
        }
        Error(int code, String msg, int httpStatus) {
            this.code = code;
            this.msg = msg;
            this.httpStatus = httpStatus;
        }
    }

    public static class Result {
        public transient int httpStatus = 200;
        public int code = 0;
        public String msg = "";
        public Object data;
    }

    public static class RawResult {
        public Object data;
    }

    public static Result result(final Object data) {
        Result result = new Result();
        result.code = 0;
        if (data instanceof List || data.getClass().isArray()) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("list", data);
            result.data = m;
        } else if (data instanceof Collection) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("list", new ArrayList<Object>((Collection) data));
            result.data = m;
        } else {
            result.data = data;
        }
        return result;
    }

    public static RawResult rawResult(Object data) {
        RawResult result = new RawResult();
        result.data = data;
        return result;
    }

    public static Result ok() {
        return error(Error.Ok.code, Error.Ok.msg, null, 200);
    }

    public static Result error(Error error) {
        return error(error.code, error.msg, null, error.httpStatus);
    }

    public static Result error(int code, String msg) {
        return error(code, msg, null, 200);
    }

    public static Result error(Error error, Object data) {
        return error(error.code, error.msg, data, error.httpStatus);
    }

    public static Result error(int code, String msg, Object data) {
        return error(code, msg, data, 200);
    }

    public static Result error(int code, String msg, Object data, int httpStatus) {
        Result result = new Result();
        result.code = code;
        result.msg = msg;
        result.data = data;
        if (httpStatus != 0) {
            result.httpStatus = httpStatus;
        }
        return result;
    }
}
