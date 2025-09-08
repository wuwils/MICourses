package com.basic.controller.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@Slf4j
public class ResponseAdviser implements ResponseBodyAdvice<Object> {

    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    public Object beforeBodyWrite(
        Object body,
        MethodParameter returnType,
        MediaType selectedContentType,
        Class<? extends HttpMessageConverter<?>> selectedConverterType,
        ServerHttpRequest request,
        ServerHttpResponse response) {

        if (body instanceof ResponseUtils.Result) {
            ResponseUtils.Result r = (ResponseUtils.Result) body;
            if (r.httpStatus != 0) {
                response.setStatusCode(HttpStatus.resolve(r.httpStatus));
            }
            return r;
        } else if (body instanceof ResponseUtils.Error) {
            ResponseUtils.Result r =  ResponseUtils.error((ResponseUtils.Error)body);
            if (r.httpStatus != 0) {
                response.setStatusCode(HttpStatus.resolve(r.httpStatus));
            }
            return r;
        } else if (body instanceof ResponseUtils.RawResult) {
            return ((ResponseUtils.RawResult) body).data;
        } else if (body instanceof String) {
            return body;
        } else {
            return ResponseUtils.result(body);
        }
    }
}
